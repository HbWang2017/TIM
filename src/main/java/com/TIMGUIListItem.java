/**
 *@author
 *LI_Yichen
 *ID: 16130120145
 *e-mail: niuqiao2010@163.com
 */

package com;

import javax.swing.*;
import javax.swing.ListCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;

public class TIMGUIListItem extends JPanel implements ListCellRenderer {

    private BackEnd Control;
    private JButton selected, ban_selected;
    private JPanel pad = new JPanel();
    private JTextField ID, SCity, TCity, num, date, price;
    private static final int col_width = 200;
    private static final int row_height = 60;
    private static final int selection_flag_width = 100;
    private static final int selection_flag_size = 30;
    private static final int font_size = 20;

    TIMGUIListItem(BackEnd Control){

        //布局
        FlowLayout f = (FlowLayout)getLayout();
        f.setHgap(0);//水平间距
        f.setVgap(0);//组件垂直间距
        setLayout(f);
        //初始化
        this.Control = Control;

        JPanel pad = new JPanel();
        ID = new JTextField();
        SCity = new JTextField();
        TCity = new JTextField();
        num = new JTextField();
        date = new JTextField();
        price = new JTextField();
        selected = new JButton("\uD83D\uDD33");
        ban_selected = new JButton("\uD83D\uDEAB");
        ID.setEnabled(false);
        SCity.setEnabled(false);
        TCity.setEnabled(false);
        num.setEnabled(false);
        date.setEnabled(false);
        price.setEnabled(false);
        ID.setLayout(f);
        SCity.setLayout(f);
        TCity.setLayout(f);
        date.setLayout(f);
        num.setLayout(f);
        price.setLayout(f);
        selected.setLayout(f);
        ban_selected.setLayout(f);
        //装饰
        ID.setPreferredSize(new Dimension(col_width, row_height));
        ID.setBackground(Color.WHITE);
        ID.setForeground(Color.GRAY);
        ID.setHorizontalAlignment(JTextField.CENTER);
        ID.setFont(new Font("TimesRoman",Font.BOLD,font_size));
        SCity.setPreferredSize(new Dimension(col_width, row_height));
        SCity.setBackground(Color.WHITE);
        SCity.setForeground(Color.GRAY);
        SCity.setHorizontalAlignment(JTextField.CENTER);
        SCity.setFont(new Font("TimesRoman",Font.BOLD,font_size));
        TCity.setPreferredSize(new Dimension(col_width, row_height));
        TCity.setBackground(Color.WHITE);
        TCity.setForeground(Color.GRAY);
        TCity.setHorizontalAlignment(JTextField.CENTER);
        TCity.setFont(new Font("TimesRoman",Font.BOLD,font_size));
        date.setPreferredSize(new Dimension(col_width, row_height));
        date.setBackground(Color.WHITE);
        date.setForeground(Color.GRAY);
        date.setHorizontalAlignment(JTextField.CENTER);
        date.setFont(new Font("TimesRoman",Font.BOLD,font_size));
        num.setPreferredSize(new Dimension(col_width, row_height));
        num.setBackground(Color.WHITE);
        num.setForeground(Color.GRAY);
        num.setHorizontalAlignment(JTextField.CENTER);
        num.setFont(new Font("TimesRoman",Font.BOLD,font_size));
        price.setPreferredSize(new Dimension(col_width, row_height));
        price.setBackground(Color.WHITE);
        price.setForeground(Color.GRAY);
        price.setHorizontalAlignment(JTextField.CENTER);
        price.setFont(new Font("TimesRoman",Font.BOLD,font_size));
        selected.setPreferredSize(new Dimension(selection_flag_width, row_height));
        selected.setBackground(Color.WHITE);
        selected.setForeground(Color.GREEN);
        selected.setHorizontalAlignment(JTextField.CENTER);
        selected.setFont(new Font("TimesRoman",Font.BOLD,selection_flag_size));
        ban_selected.setPreferredSize(new Dimension(selection_flag_width, row_height));
        ban_selected.setBackground(Color.WHITE);
        ban_selected.setForeground(Color.RED);
        ban_selected.setHorizontalAlignment(JTextField.CENTER);
        ban_selected.setFont(new Font("TimesRoman",Font.BOLD,selection_flag_size));
    }
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        //获取列表项

        TicketInfo ti = (TicketInfo) value;

        if (value != null) {
            //组装
            pad.add(ID);
            pad.add(SCity);
            pad.add(TCity);
            pad.add(date);
            pad.add(num);
            pad.add(price);
            try {
				if(Control.checkNum(ti.getID(), ti.getDate()))
				    pad.add(selected);
				else
				    pad.add(ban_selected);
			} 
            catch (SQLException e) {e.printStackTrace();}
            add(pad);
            ID.setText(ti.getID());
            SCity.setText(ti.getSCity());
            TCity.setText(ti.getTCity());
            num.setText(ti.getNum());
            date.setText(ti.getDate());
            price.setText(ti.getPrice());
        }
        if (isSelected)
        {
            ID.setBackground(Color.yellow);
            SCity.setBackground(Color.yellow);
            TCity.setBackground(Color.yellow);
            date.setBackground(Color.yellow);
            num.setBackground(Color.yellow);
            price.setBackground(Color.yellow);
            selected.setBackground(Color.yellow);
            ID.setForeground(Color.RED);
            SCity.setForeground(Color.RED);
            TCity.setForeground(Color.RED);
            date.setForeground(Color.RED);
            num.setForeground(Color.RED);
            price.setForeground(Color.RED);
            selected.setText("☑");
            ban_selected.setBackground(Color.yellow);
        }
        else
        {
            ID.setBackground(Color.WHITE);
            SCity.setBackground(Color.WHITE);
            TCity.setBackground(Color.WHITE);
            date.setBackground(Color.WHITE);
            num.setBackground(Color.WHITE);
            price.setBackground(Color.WHITE);
            selected.setBackground(Color.WHITE);
            ban_selected.setBackground(Color.WHITE);
            ID.setForeground(Color.GRAY);
            SCity.setForeground(Color.GRAY);
            TCity.setForeground(Color.GRAY);
            date.setForeground(Color.GRAY);
            num.setForeground(Color.GRAY);
            price.setForeground(Color.GRAY);
            selected.setText("\uD83D\uDD33");
        }
        return this;
    }
}
