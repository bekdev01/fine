package uz.pdp.bot.service;

import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import static uz.pdp.util.Constant.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class BotService {

    public static SendMessage getMainMenu(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(SEARCH_USER, SEARCH_CAR),
                List.of(SEARCH_FINE)
        ));
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), WELCOME);
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }
    public static EditMessageText getMainMenuEdit(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(SEARCH_USER, SEARCH_CAR),
                List.of(SEARCH_FINE)
        ));
        EditMessageText editMessageText = new EditMessageText(CHOOSE_ACTION);
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setReplyMarkup(markup);
        editMessageText.setMessageId(message.getMessageId());
        return editMessageText;
    }

    public static EditMessageText searchFine(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(SEARCH_CAR_BY_MONTH_AND_NUMBER,
                        SEARCH_CAR_BY_NUMBER,
                        SEARCH_CAR_BY_USER_ID),
                List.of(MENU)
        ));
        EditMessageText editMessageText = new EditMessageText(CHOOSE_ACTION);
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setReplyMarkup(markup);
        editMessageText.setMessageId(message.getMessageId());
        return editMessageText;
    }

    public static EditMessageText searchCar(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(MENU)
        ));
        EditMessageText editMessageText = new EditMessageText(SEARCH_CAT_TEXT);
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setReplyMarkup(markup);
        editMessageText.setMessageId(message.getMessageId());
        return editMessageText;
    }
    public static EditMessageText searchUser(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(MENU)
        ));
        EditMessageText editMessageText = new EditMessageText(SEARCH_USER_TEXT);
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setReplyMarkup(markup);
        editMessageText.setMessageId(message.getMessageId());
        return editMessageText;
    }
    public static SendMessage exception(Update update, String messageText){
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(MENU)
        ));
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), messageText);
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }

    public static SendMessage getCarById(Update update, long id) throws SQLException {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(MENU)
        ));
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), Objects.requireNonNull(DataService.getCarsById(id)));
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }
    public static SendMessage getUserById(Update update, long id) throws SQLException {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(MENU)
        ));
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), Objects.requireNonNull(DataService.getUsersById(id)));
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }

    public static EditMessageText getMonth(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(MENU)
        ));
        EditMessageText editMessageText = new EditMessageText(MONTH_TEXT);
        editMessageText.setChatId(message.getChatId().toString());
        editMessageText.setReplyMarkup(markup);
        editMessageText.setMessageId(message.getMessageId());
        return editMessageText;
    }

    public static SendMessage getNumber(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(MENU)
        ));
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(),CAR_NUMBER_TEXT);
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }
    public static EditMessageText getNumberEdit(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(MENU)
        ));
        EditMessageText sendMessage = new EditMessageText(CAR_NUMBER_TEXT);
        sendMessage.setMessageId(message.getMessageId());
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }
    public static EditMessageText getFinesByUserId(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(MENU)
        ));
        EditMessageText sendMessage = new EditMessageText(USER_ID_TEXT);
        sendMessage.setMessageId(message.getMessageId());
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }

    public static SendMessage getFineByMonthAndNumber(Update update, int month, String num) throws SQLException {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(MENU)
        ));
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), Objects.requireNonNull(DataService.getFineByMonthAndCarId(month,num)));
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }

    public static SendMessage getFineByCarNumber(Update update, String number) throws SQLException {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(MENU)
        ));
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), Objects.requireNonNull(DataService.getFineByCarNumber(number)));
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }

    public static SendMessage getFineByUserIdEnd(Update update, long id) throws SQLException {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        InlineKeyboardMarkup markup = InlineKeyboardService.createMarkup(List.of(
                List.of(MENU)
        ));
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), Objects.requireNonNull(DataService.getFineByUserIdNumber(id)));
        sendMessage.setReplyMarkup(markup);
        return sendMessage;
    }
}

