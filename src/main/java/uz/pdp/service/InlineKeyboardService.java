package uz.pdp.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboardService  {

    public static InlineKeyboardMarkup createMarkup(List<List<String>> rows){
        List<List<InlineKeyboardButton>> rowList=new ArrayList<>();
        for (List<String> row : rows) {
            List<InlineKeyboardButton> dRow=new ArrayList<>();
            for (String word : row) {
                InlineKeyboardButton button;
                    button=new InlineKeyboardButton(word);
                    button.setCallbackData(word);
                dRow.add(button);
            }
            rowList.add(dRow);
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}
