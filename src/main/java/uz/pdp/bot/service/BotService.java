package uz.pdp.bot.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static uz.pdp.util.Constant.*;

import java.util.List;

public class BotService {

    public static SendMessage getMainMenu(Update update){
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(SEARCH_USER, SEARCH_CAR)
        ));
        SendMessage sendMessage=new SendMessage(message.getChatId().toString(),WELCOME);
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }
}
