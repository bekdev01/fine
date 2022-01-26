package uz.pdp.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static uz.pdp.util.Constant.*;

public class FineBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {

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
