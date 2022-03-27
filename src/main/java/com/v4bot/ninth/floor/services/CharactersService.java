package com.v4bot.ninth.floor.services;

import com.v4bot.ninth.floor.entities.Archetype;
import com.v4bot.ninth.floor.entities.Player;
import com.v4bot.ninth.floor.repositories.ArchetypesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Transactional
public class CharactersService {
    private final ArchetypesRepository archetypesRepository;
    private final ChatPlayersService chatPlayersService;




    public List<Archetype> getNewPlayableCharacter(Message input, SendMessage output) {
        List<Player> players = chatPlayersService.findPlayersByChatId(input.getChatId());

        //ищем наименее популярный архетип которого еще нет ни у кого их игроков
        Optional<Archetype> chosenOpt = archetypesRepository.findAllByOrderByPlayFrequencyAsc().stream()
            .filter(archetype -> players.stream()
                    .map(Player::getCharacter)
                    .noneMatch(character -> !isNull(character) && !isNull(character.getArchetype()) && archetype.getId().equals(character.getArchetype().getId())))
                .findFirst();
        if(chosenOpt.isPresent()) {
            Archetype chosen = chosenOpt.get();
            chosen.setPlayFrequency(chosen.getPlayFrequency()+1);
            output.setText(prepareArchetypeDescription(chosen));
        }
        //todo если все архетипы заняты
        return null;
    }

    private String prepareArchetypeDescription(Archetype archetype) {
        return "Вы " + archetype.getDescription() + "\n"+ archetype.getIndicatorsFormatted();
        //todo разделять архетипы для неписей и нет
    }
}
