package SkyPro.DmitrievIvanCommandHomeWork.listener;


import SkyPro.DmitrievIvanCommandHomeWork.modelAndConstants.Recommendation;
import SkyPro.DmitrievIvanCommandHomeWork.repositoryTelegramBot.CommandRepository;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import SkyPro.DmitrievIvanCommandHomeWork.dynamicService.DynamicRuleService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import SkyPro.DmitrievIvanCommandHomeWork.repository.RecommendationsRepository;


import java.util.List;
import java.util.UUID;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {


    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);//нужен для проверки работы процесса через терминал

    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    private CommandRepository commandRepository;
    @Autowired
    private DynamicRuleService dynamicRuleService;

    private final RecommendationsRepository recommendationsRepository;

    public TelegramBotUpdatesListener(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        for (int i = 0; i < updates.size(); i++) {
            Update update = updates.get(i);
            String textUpdate = update.message().text();
            Long chatId = update.message().chat().id();

            if (textUpdate != null && textUpdate.startsWith("/recommend") && textUpdate.length() > 11) {
                String userNameInChat = textUpdate.substring(11).trim();
                String userId = recommendationsRepository.findUserIdByUserName(userNameInChat)
                        .orElseThrow(() -> new EnteredIncorrectlyException("Неверно введены данные, либо равны null"));

                UUID userUuid = UUID.fromString(userId);
                List<Recommendation> recommendationsByUserId = dynamicRuleService.getRecommendationsByUserId(userUuid);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Новые продукты для вас:");
                recommendationsByUserId.forEach(r -> stringBuilder.append(r.toTelegramString()));
                SendMessage sendMessage = new SendMessage(chatId, stringBuilder.toString());
                telegramBot.execute(sendMessage);
                return UpdatesListener.CONFIRMED_UPDATES_ALL;

            }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
