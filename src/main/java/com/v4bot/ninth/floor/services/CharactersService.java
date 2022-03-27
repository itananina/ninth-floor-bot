package com.v4bot.ninth.floor.services;

import com.v4bot.ninth.floor.entities.*;
import com.v4bot.ninth.floor.repositories.ArchetypesRepository;
import com.v4bot.ninth.floor.repositories.PlayableCharactersRepository;
import com.v4bot.ninth.floor.repositories.StatesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CharactersService {
    private final ArchetypesRepository archetypesRepository;
    private final ChatPlayersService chatPlayersService;
    private final ImagesService imagesService;
    private final StatesRepository statesRepository;
    private final PlayableCharactersRepository playableCharactersRepository;

    public void getNewPlayableCharacter(Message input, SendPhoto imgOutput) {
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

            Player player = chatPlayersService.findPlayerByUsername(input.getFrom().getUserName());
            PlayableCharacter character = new PlayableCharacter(chosen);
            State state = statesRepository.findById(chosen.getState().getId()).orElse(null);
            if(state!=null) {
                State stateCopy = (State) state.clone();
                stateCopy = statesRepository.save(stateCopy);
                character.setState(stateCopy);
                saveCharacter(character);
            } else {
                log.info("Не удалось скопировать состяние архетипа при создании игрового персонажа для {}",input.getFrom().getUserName());
            }
            player.setCharacter(character);
            chatPlayersService.savePlayer(player);

            imagesService.setPhotoWithCaptionByFilesPath(imgOutput, "/archetypes/"+chosen.getCode(), prepareArchetypeDescription(chosen));
            imgOutput.setCaption(prepareArchetypeDescription(chosen));
        } else {
            log.info("Игрок {} не получил персонажа, все архетипы заняты", input.getFrom().getUserName());
            //todo если все архетипы заняты
        }
    }

    private String prepareArchetypeDescription(Archetype archetype) {
        return "Вы " + archetype.getDescription() + "\n"+ archetype.getIndicatorsFormatted();
        //todo разделять архетипы для неписей и нет
    }

    public String getPlayableCharacterInfoByPlayerUsername(String username) {
        Player player = chatPlayersService.findPlayerByUsername(username);
        if(player.getCharacter()!=null && player.getCharacter().getArchetype() !=null) {
           return prepareArchetypeDescription(player.getCharacter().getArchetype());
        }
        return null;
    }

    public void saveCharacter(PlayableCharacter character) {
        playableCharactersRepository.save(character);
    }
}
