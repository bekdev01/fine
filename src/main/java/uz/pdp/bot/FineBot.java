package uz.pdp.bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import uz.pdp.service.BotService;
import uz.pdp.util.enums.SearchType;

import static uz.pdp.util.enums.SearchType.*;

import static uz.pdp.util.Constant.*;

public class FineBot extends TelegramLongPollingBot {
    SearchType searchType = null;
    int month;
    boolean isPaid;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                String text = message.getText();
                if (searchType == null) {
                    if (text.equals(START))
                        execute(BotService.getMainMenu(update));
                } else {
                    switch (searchType) {
                        case CAR -> {
                            try {
                                execute(BotService.getCarByNumber(update, text));
                            } catch (Exception e) {
                                execute(BotService.exception(update, e.getMessage()));
                            }
                        }
                        case USER -> {
                            try {
                                execute(BotService.getUserByPassportCode(update, text));
                            } catch (Exception e) {
                                execute(BotService.exception(update, e.getMessage() == null ? "Exception" : e.getMessage()));
                            }
                        } case MONTH -> {
                            try {
                                int num = Integer.parseInt(text);
                                if(num<1 || num>12)throw new Exception();
                                month=num;
                                execute(BotService.getNumber(update));
                                searchType=MONTH_NUMBER;
                            } catch (Exception e) {
                                execute(BotService.exception(update, e.getMessage() == null ? "Exception" : e.getMessage()));
                            }
                        }
                        case MONTH_NUMBER -> {
                                try {
                                    execute(BotService.getFineByMonthAndNumber(update,month,text));
                                    searchType=MONTH_NUMBER;
                                }catch (Exception e) {
                                    execute(BotService.exception(update, e.getMessage() == null ? "Exception" : e.getMessage()));
                                }
                        }
                        case NUMBER -> {
                            try {
                                execute(BotService.getFineByCarNumber(update,text));
                            }catch (Exception e){
                                execute(BotService.exception(update, e.getMessage() == null ? "Exception" : e.getMessage()));
                            }
                        }
                        case FINE_USER -> {
                            try {
                                long id = Long.parseLong(text);
                                execute(BotService.getFineByUserIdEnd(update,id,isPaid));
                            } catch (Exception e) {
                                execute(BotService.exception(update, e.getMessage() == null ? "Exception" : e.getMessage()));
                            }
                        }

                    }
                }
            }
        } else {
            String data = update.getCallbackQuery().getData();
            switch (data) {
                case MENU -> {
                    execute(BotService.getMainMenuEdit(update));
                    searchType = null;
                }
                case SEARCH_USER -> {
                    execute(BotService.searchUser(update));
                    searchType = USER;
                }
                case SEARCH_CAR -> {
                    execute(BotService.searchCar(update));
                    searchType = CAR;
                }
                case SEARCH_FINE -> execute(BotService.searchFine(update));
                case SEARCH_CAR_BY_MONTH_AND_NUMBER -> {
                    execute(BotService.getMonth(update));
                    searchType=MONTH;
                }
                case SEARCH_CAR_BY_NUMBER -> {
                    execute(BotService.getNumberEdit(update));
                    searchType=NUMBER;
                }
                case SEARCH_CAR_BY_USER_ID -> execute(BotService.getFineMenu(update));
                case PAID_FINE,UNPAID_FINE -> {
                    execute(BotService.getFinesByUserId(update));
                    searchType=FINE_USER;
                    isPaid=data.equals(PAID_FINE);
                }
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