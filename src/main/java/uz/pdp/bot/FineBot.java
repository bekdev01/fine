package uz.pdp.bot;

import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
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
        long start = System.currentTimeMillis();
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
                                SendMessage sendMessage = BotService.getCarByNumber(update, text);
                                long end = System.currentTimeMillis();
                                sendMessage.setText(sendMessage.getText().concat("\nJAVA Execution Time: ").concat(String.valueOf(end-start)).concat(" ms"));
                                execute(sendMessage);
                            } catch (Exception e) {
                                execute(BotService.exception(update, e.getMessage()));
                            }
                        }
                        case USER -> {
                            try {
                                SendMessage sendMessage = BotService.getUserByPassportCode(update, text);
                                long end = System.currentTimeMillis();
                                sendMessage.setText(sendMessage.getText().concat("\nJAVA Execution Time: ").concat(String.valueOf(end-start)).concat(" ms"));
                                execute(sendMessage);
                            } catch (Exception e) {
                                execute(BotService.exception(update, e.getMessage() == null ? "Exception" : e.getMessage()));
                            }
                        } case MONTH -> {
                            try {
                                int num = Integer.parseInt(text);
                                if(num<1 || num>12)throw new Exception();
                                month=num;
                                SendMessage sendMessage = BotService.getNumber(update);
                                long end = System.currentTimeMillis();
                                sendMessage.setText(sendMessage.getText().concat("\nJAVA Execution Time: ").concat(String.valueOf(end-start)).concat(" ms"));
                                execute(sendMessage);
                                searchType=MONTH_NUMBER;
                            } catch (Exception e) {
                                execute(BotService.exception(update, e.getMessage() == null ? "Exception" : e.getMessage()));
                            }
                        }
                        case MONTH_NUMBER -> {
                                try {
                                    SendMessage sendMessage = BotService.getFineByMonthAndNumber(update, month, text);
                                    long end = System.currentTimeMillis();
                                    sendMessage.setText(sendMessage.getText().concat("\nJAVA Execution Time: ").concat(String.valueOf(end-start)).concat(" ms"));
                                    execute(sendMessage);
                                    searchType=MONTH_NUMBER;
                                }catch (Exception e) {
                                    execute(BotService.exception(update, e.getMessage() == null ? "Exception" : e.getMessage()));
                                }
                        }
                        case NUMBER -> {
                            try {
                                SendMessage sendMessage = BotService.getFineByCarNumber(update, text);
                                long end = System.currentTimeMillis();
                                sendMessage.setText(sendMessage.getText().concat("\nJAVA Execution Time: ").concat(String.valueOf(end-start)).concat(" ms"));
                                execute(sendMessage);
                            }catch (Exception e){
                                execute(BotService.exception(update, e.getMessage() == null ? "Exception" : e.getMessage()));
                            }
                        }
                        case FINE_USER -> {
                            try {
                                long id = Long.parseLong(text);
                                SendMessage sendMessage = BotService.getFineByUserIdEnd(update, id, isPaid);
                                long end = System.currentTimeMillis();
                                sendMessage.setText(sendMessage.getText().concat("\nJAVA Execution Time: ").concat(String.valueOf(end-start)).concat(" ms"));
                                execute(sendMessage);
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
                    EditMessageText sendMessage = BotService.getMainMenuEdit(update);
                    long end = System.currentTimeMillis();
                    sendMessage.setText(sendMessage.getText().concat("\nJAVA Execution Time: ").concat(String.valueOf(end-start)).concat(" ms"));
                    execute(sendMessage);
                    searchType = null;
                }
                case SEARCH_USER -> {
                    EditMessageText sendMessage =BotService.searchUser(update);
                    long end = System.currentTimeMillis();
                    sendMessage.setText(sendMessage.getText().concat("\nJAVA Execution Time: ").concat(String.valueOf(end-start)).concat(" ms"));
                    execute(sendMessage);
                    searchType = USER;
                }
                case SEARCH_CAR -> {
                    EditMessageText sendMessage =BotService.searchCar(update);
                    long end = System.currentTimeMillis();
                    sendMessage.setText(sendMessage.getText().concat("\nJAVA Execution Time: ").concat(String.valueOf(end-start)).concat(" ms"));
                    execute(sendMessage);
                    searchType = CAR;
                }
                case SEARCH_FINE -> {
                    EditMessageText sendMessage =BotService.searchFine(update);
                    long end = System.currentTimeMillis();
                    sendMessage.setText(sendMessage.getText().concat("\nJAVA Execution Time: ").concat(String.valueOf(end-start)).concat(" ms"));
                    execute(sendMessage);
                }
                case SEARCH_CAR_BY_MONTH_AND_NUMBER -> {
                    EditMessageText sendMessage =BotService.getMonth(update);
                    long end = System.currentTimeMillis();
                    sendMessage.setText(sendMessage.getText().concat("\nJAVA Execution Time: ").concat(String.valueOf(end-start)).concat(" ms"));
                    execute(sendMessage);
                    searchType=MONTH;
                }
                case SEARCH_CAR_BY_NUMBER -> {
                    EditMessageText sendMessage = BotService.getNumberEdit(update);
                    long end = System.currentTimeMillis();
                    sendMessage.setText(sendMessage.getText().concat("\nJAVA Execution Time: ").concat(String.valueOf(end-start)).concat(" ms"));
                    execute(sendMessage);
                    searchType=NUMBER;
                }
                case SEARCH_CAR_BY_USER_ID -> {
                    EditMessageText sendMessage =BotService.getFineMenu(update);
                    long end = System.currentTimeMillis();
                    sendMessage.setText(sendMessage.getText().concat("\nJAVA Execution Time: ").concat(String.valueOf(end-start)).concat(" ms"));
                    execute(sendMessage);
                }
                case PAID_FINE,UNPAID_FINE -> {
                    EditMessageText sendMessage =BotService.getFinesByUserId(update);
                    long end = System.currentTimeMillis();
                    sendMessage.setText(sendMessage.getText().concat("\nJAVA Execution Time: ").concat(String.valueOf(end-start)).concat(" ms"));
                    execute(sendMessage);
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