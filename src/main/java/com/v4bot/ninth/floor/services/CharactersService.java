package com.v4bot.ninth.floor.services;

import com.v4bot.ninth.floor.data.Context;
import com.v4bot.ninth.floor.entities.*;
import com.v4bot.ninth.floor.repositories.ArchetypesRepository;
import com.v4bot.ninth.floor.repositories.PlayableCharactersRepository;
import com.v4bot.ninth.floor.repositories.StatesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public void getNewPlayableCharacter(Context context) {
        if(context!=null && context.getPlayer()!=null) {
            List<Player> players = chatPlayersService.findPlayersByChatId(context.getChatId());

            //ищем наименее популярный архетип которого еще нет ни у кого их игроков
            Optional<Archetype> chosenOpt = archetypesRepository.findAllByIsPlayableOrderByPlayFrequencyAsc(true).stream()
                    .filter(archetype -> players.stream()
                            .map(Player::getCharacter)
                            .noneMatch(character -> !isNull(character) && !isNull(character.getArchetype()) && archetype.getId().equals(character.getArchetype().getId())))
                    .findFirst();
            //todo checksum команды
            Player player = chatPlayersService.findPlayerByUsername(context.getPlayer().getUsername());
            if (chosenOpt.isPresent() && player != null) {
                Archetype chosen = chosenOpt.get();
                chosen.setPlayFrequency(chosen.getPlayFrequency() + 1);

                PlayableCharacter character = new PlayableCharacter(chosen);
                State state = statesRepository.findById(chosen.getState().getId()).orElse(null);
                if (state != null) {
                    State stateCopy = (State) state.clone();
                    stateCopy = statesRepository.save(stateCopy);
                    character.setState(stateCopy);
                    saveCharacter(character);
                } else {
                    log.info("Не удалось скопировать состяние архетипа при создании игрового персонажа для {}", context.getPlayer().getUsername());
                }
                player.setCharacter(character);
                chatPlayersService.savePlayer(player);

                imagesService.setPhotoWithCaptionByFilesPath(context.getImgResponse(), "archetypes/" + chosen.getCode(), prepareArchetypeDescription(chosen));
                context.getImgResponse().setCaption(prepareArchetypeDescription(chosen));
            } else {
                log.info("Игрок {} не получил персонажа, все архетипы заняты", context.getPlayer().getUsername());
                //todo если все архетипы заняты
            }
        } else {
            log.info("Игрок не получил персонажа, не удалось найти игрока в контексте");
        }
    }

    private String prepareArchetypeDescription(Archetype archetype) {
        return "Вы " + archetype.getName() + "\n" +
                "Ваш архетип: " + archetype.getDescription() + "\n"+
                archetype.getIndicatorsFormatted();
    }

    private String prepareCharacterDescription(PlayableCharacter character) {
        return  character.getName() + "\n" +
                "Архетип: " + character.getArchetype().getDescription() + "\n"+
                character.getIndicatorsFormatted();
    }

    private String getPlayableCharacterInfoByPlayerUsername(String username) {
        Player player = chatPlayersService.findPlayerByUsername(username);
        if(player!=null && player.getCharacter()!=null && player.getCharacter().getArchetype() !=null) {
           return prepareCharacterDescription(player.getCharacter());
        }
        return null;
    }

    public void getPlayableCharacterInfoByPlayerUsername(Context context, String addedText) {
        String description = getPlayableCharacterInfoByPlayerUsername(context.getPlayer().getUsername());
        if(description!=null && description.length()>0) {
            imagesService.setPhotoWithCaptionByFilesPath(context.getImgResponse(),
                    "archetypes/"+context.getPlayer().getCharacter().getArchetype().getCode(),
                    (addedText!=null? addedText + "\n": "") + description);
        } else {
            log.info("Ошибка при поиске перснажа игрока {}", context.getPlayer().getUsername());
        }
    }

    public void saveCharacter(PlayableCharacter character) {
        playableCharactersRepository.save(character);
    }
}
