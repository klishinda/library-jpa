package ru.otus.homework.printer;

import org.springframework.shell.table.*;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultsPrinterImpl implements ResultsPrinter {

    @Override
    public Table printAuthors(List<Author> list) {
        String[][] table = new String[list.size()+1][2];
        TableModel model = new ArrayTableModel(table);
        TableBuilder tableBuilder = new TableBuilder(model);

        table[0][0] = "ID";
        table[0][1] = "АВТОР";
        for (int i = 0; i < list.size(); i++) {
            table[i+1][0] = String.valueOf(list.get(i).getId());
            table[i+1][1] = list.get(i).getName() + " " + list.get(i).getSurname();
        }

        return tableBuilder.addFullBorder(BorderStyle.fancy_light).build();
    }

    @Override
    public Table printGenres(List<Genre> list) {
        String[][] table = new String[list.size()+1][2];
        TableModel model = new ArrayTableModel(table);
        TableBuilder tableBuilder = new TableBuilder(model);

        table[0][0] = "ID";
        table[0][1] = "ЖАНР";
        for (int i = 0; i < list.size(); i++) {
            table[i+1][0] = String.valueOf(list.get(i).getId());
            table[i+1][1] = list.get(i).getName();
        }

        return tableBuilder.addFullBorder(BorderStyle.fancy_light).build();
    }

    @Override
    public Table printComments(List<Comment> list) {
        String[][] table = new String[list.size()+1][5];
        TableModel model = new ArrayTableModel(table);
        TableBuilder tableBuilder = new TableBuilder(model);
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        table[0][0] = "ID";
        table[0][1] = "ОЦЕНКА";
        table[0][2] = "ПОЛЬЗОВАТЕЛЬ";
        table[0][3] = "КОММЕНТАРИЙ";
        table[0][4] = "ДАТА ДОБАВЛЕНИЯ КОММЕНТАРИЯ";
        for (int i = 0; i < list.size(); i++) {
            table[i+1][0] = String.valueOf(list.get(i).getId());
            table[i+1][1] = String.valueOf(list.get(i).getMark());
            table[i+1][2] = list.get(i).getUserName();
            table[i+1][3] = list.get(i).getComment();
            table[i+1][4] = formatter.format(list.get(i).getCreateDate());
        }

        return tableBuilder.addFullBorder(BorderStyle.fancy_light).build();
    }

    @Override
    public Table printBooks(List<Book> list) {
        String[][] table = new String[list.size()+1][4];
        TableModel model = new ArrayTableModel(table);
        TableBuilder tableBuilder = new TableBuilder(model);

        table[0][0] = "НАЗВАНИЕ КНИГИ";
        table[0][1] = "ЖАНР(Ы)";
        table[0][2] = "ИМЯ АВТОРА(ОВ)";
        table[0][3] = "КОЛИЧЕСТВО СТРАНИЦ";
        for (int i = 0; i < list.size(); i++) {
            StringBuilder authors = new StringBuilder();
            table[i+1][0] = list.get(i).getName();
            for (Author a : list.get(i).getAuthors()) {
                if (authors.toString().equals("")) {
                    authors.append(a.getName()).append(" ").append(a.getSurname());
                }
                else {
                    authors.append(", ").append(a.getName()).append(" ").append(a.getSurname());
                }
            }
            table[i+1][1] = list.get(i).getGenres().stream().map(Genre::getName).collect(Collectors.joining(", "));
            table[i+1][2] = authors.toString();
            table[i+1][3] = String.valueOf(list.get(i).getPages());
        }

        return tableBuilder.addFullBorder(BorderStyle.fancy_light).build();
    }

    @Override
    public Table printBookComments(List<Book> list) {
        int max_value = 1;
        for (Book book : list) {
            for (Comment comment : book.getComments()) {
                max_value++;
            }
        }

        String[][] table = new String[max_value][4];
        TableModel model = new ArrayTableModel(table);
        TableBuilder tableBuilder = new TableBuilder(model);
        int iterator = 1;

        table[0][0] = "НАЗВАНИЕ КНИГИ";
        table[0][1] = "ОЦЕНКА";
        table[0][2] = "ИМЯ ПОЛЬЗОВАТЕЛЯ";
        table[0][3] = "КОММЕНТАРИЙ";
        for (Book book : list) {
            for (Comment comment : book.getComments()) {
                table[iterator][0] = book.getName();
                table[iterator][1] = String.valueOf(comment.getMark());
                table[iterator][2] = comment.getUserName();
                table[iterator][3] = comment.getComment();
                iterator++;
            }
        }

        return tableBuilder.addFullBorder(BorderStyle.fancy_light).build();
    }
}
