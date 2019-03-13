package ru.otus.homework.printer;

import org.springframework.shell.table.*;
import org.springframework.stereotype.Service;
import ru.otus.homework.model.Author;
import ru.otus.homework.model.Book;
import ru.otus.homework.model.Comment;
import ru.otus.homework.model.Genre;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ResultsPrinterImpl implements ResultsPrinter {

    @Override
    public Table printAuthors(Set<Author> list) {
        String[][] table = new String[list.size()+1][1];
        TableModel model = new ArrayTableModel(table);
        TableBuilder tableBuilder = new TableBuilder(model);

        int i = 0;
        table[0][0] = "АВТОР";
        for (Author a : list) {
            table[++i][0] = a.getName() + " " + a.getSurname();
        }

        return tableBuilder.addFullBorder(BorderStyle.fancy_light).build();
    }

    @Override
    public Table printGenres(Set<Genre> list) {
        String[][] table = new String[list.size()+1][1];
        TableModel model = new ArrayTableModel(table);
        TableBuilder tableBuilder = new TableBuilder(model);

        int i = 0;
        table[0][0] = "ЖАНР";
        for (Genre g : list) {
            table[++i][0] = g.getName();
        }

        return tableBuilder.addFullBorder(BorderStyle.fancy_light).build();
    }

    @Override
    public Table printComments(Set<Comment> list) {
        String[][] table = new String[list.size()+1][3];
        TableModel model = new ArrayTableModel(table);
        TableBuilder tableBuilder = new TableBuilder(model);

        int i = 0;
        table[0][0] = "ОЦЕНКА";
        table[0][1] = "ПОЛЬЗОВАТЕЛЬ";
        table[0][2] = "КОММЕНТАРИЙ";
        for (Comment c : list) {
            System.out.println(i + " " + c.getComment());
            table[++i][0] = String.valueOf(c.getMark());
            table[i][1] = c.getUserName();
            table[i][2] = c.getComment();
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
            Set<Author> authorList = list.get(i).getAuthors();
            if (authorList != null) {
                for (Author a : authorList) {
                    if (authors.toString().equals("")) {
                        authors.append(a.getName()).append(" ").append(a.getSurname());
                    } else {
                        authors.append(", ").append(a.getName()).append(" ").append(a.getSurname());
                    }
                }
            }

            if (list.get(i).getGenres() != null) {
                table[i + 1][1] = list.get(i).getGenres().stream().map(Genre::getName).collect(Collectors.joining(", "));
            }
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
