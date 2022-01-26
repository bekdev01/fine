package uz.pdp;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.pdp.bot.FineBot;

public class Main {
    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi api=new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(new FineBot());
        System.out.println("FINE BOT STARTED");
    }
}
