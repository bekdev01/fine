package uz.pdp.bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.pdp.bot.service.BotService;
import uz.pdp.util.enums.SearchType;

import static uz.pdp.util.Constant.*;

public class FineBot extends TelegramLongPollingBot {
    SearchType searchType = null;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                if (searchType == null){
                    switch (text) {
                        case START -> execute(BotService.getMainMenu(update));

                    }
                }else {
                    switch (searchType){
                        case CAR ->{
                            try {
                                long id=Long.parseLong(text);
                                execute(BotService.getCarById(update,id));
                            }catch (Exception e){
                                execute(BotService.exception(update, e.getMessage()));
                            }
                        }case USER ->{
                            try {
                                long id=Long.parseLong(text);
                                execute(BotService.getUserById(update,id));
                            }catch (Exception e){
                                execute(BotService.exception(update, e.getMessage()==null?"Exception":e.getMessage()));
                            }
                        }
                    }
                }
            }
        }else {
            String data = update.getCallbackQuery().getData();
            switch (data){
                case MENU -> {
                    execute(BotService.getMainMenuEdit(update));
                    searchType=null;
                }
                case SEARCH_USER -> {
                    execute(BotService.searchUser(update));
                    searchType = SearchType.USER;
                }
                case SEARCH_CAR ->{
                    execute(BotService.searchCar(update));
                    searchType = SearchType.CAR;
                }
                case SEARCH_FINE -> execute(BotService.searchFine(update));
            }
        }


    }


    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }

}