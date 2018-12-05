/**
 *@author
 *LI_Yichen
 *ID: 16130120145
 *e-mail: niuqiao2010@163.com
 */

package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TIMGUIMainWindow extends JFrame implements MouseListener, ActionListener, FocusListener {

    //面部显示基本参数
    private BackEnd Control;//后端控制
    private JTextField SCity;//始发城市
    private JTextField TCity;//目的城市
    private JTextField Date;//出发日期
    private Box Main = Box.createVerticalBox();;//总体架构
    private JPanel Banner;//顶部控制栏
    private final JPanel Foot;//底部栏
    private TIMGUIList List;//信息显示表
    private final JButton Search;//搜索按钮
    private final JButton Purchase;//购买按钮
    private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");//日期格式
    private Date curr_date;//当前日期
    private String today;//当前日期字符串
    private int selected_index = -1;//选择序号
    private TicketCollection show_collection;//展示结果

    TIMGUIMainWindow() throws SQLException{
        //默认构造函数

        //初始化相关组件
        Control = new BackEnd();
        SCity = new JTextField(20);
        TCity = new JTextField(20);
        Date = new JTextField(20);
        Banner = new JPanel();
        Foot = new JPanel();
        Search = new JButton("SEARCH");
        Purchase = new JButton("PURCHASE");
        List = new TIMGUIList(Control, SCity.getText(), TCity.getText(), Date.getText());
        refresh();//刷新列表
        curr_date = new Date();
        format.setLenient(false);
        today = format.format(curr_date);
        //设置布局
        SCity.setAlignmentX(Component.CENTER_ALIGNMENT);
        TCity.setAlignmentX(Component.CENTER_ALIGNMENT);
        Date.setAlignmentX(Component.CENTER_ALIGNMENT);
        //装饰
        SCity.setForeground(Color.RED);
        SCity.setBackground(Color.WHITE);
        SCity.setFont(new Font("TimesRoman",Font.BOLD,12));
        TCity.setForeground(Color.RED);
        TCity.setBackground(Color.WHITE);
        TCity.setFont(new Font("TimesRoman",Font.BOLD,12));
        Date.setForeground(Color.RED);
        Date.setBackground(Color.WHITE);
        Date.setFont(new Font("TimesRoman",Font.BOLD,12));
        Date.setText(today);
        Search.setForeground(Color.RED);
        Search.setBackground(Color.WHITE);
        Search.setFont(new Font("TimesRoman",Font.BOLD,12));
        Purchase.setForeground(Color.WHITE);
        Purchase.setBackground(Color.RED);
        Purchase.setFont(new Font("TimesRoman",Font.BOLD,30));
        Banner.setPreferredSize(new Dimension(0,-30));
        Banner.setBackground(Color.yellow);
        Banner.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        List.setSize(new Dimension(0, 900));
        Foot.setPreferredSize(new Dimension(0,0));
        Purchase.setPreferredSize(new Dimension(1000,50));
        //侦听
        Search.addActionListener(this);
        Purchase.addActionListener(this);
        //Banner组装
        Banner.add(Box.createHorizontalStrut(30));
        Banner.add(new JLabel("Departure："));
        Banner.add(SCity);
        Banner.add(Box.createHorizontalStrut(15));
        Banner.add(new JLabel("⇋"));
        Banner.add(Box.createHorizontalStrut(15));
        Banner.add(new JLabel("Arrival"));
        Banner.add(TCity);
        Banner.add(Box.createHorizontalStrut(15));
        Banner.add(new JLabel("Departure Date (YYYY/MM/DD)："));
        Banner.add(Date);
        Banner.add(Box.createHorizontalStrut(15));
        Banner.add(Search);
        Banner.add(Box.createHorizontalStrut(30));
        //Foot组装
        Foot.add(Purchase);
        //总窗口组装
        add(Main);
        Main.add(Banner, BorderLayout.NORTH);
        Main.add(List, BorderLayout.CENTER);
        Main.add(Foot, BorderLayout.SOUTH);
        //获取搜索结果
        show_collection = List.getSearchResult();
        //显示窗口
        addWindowListener(new WindowAdapter() { public void windowClosing(WindowEvent e) { System.exit(0); }});
        setVisible(true);
        setTitle("TIM - TICKET INFORMATION MANAGER       copyright©LIYichen-16130120145");
        setBounds(0,0,1920,1080);
        setLocationRelativeTo(null);// 窗口居中
        validate();
        System.out.println("Welcome to TIM!\n");
    }
    private void checkDateFormat(){
        //检查输入日期格式

        try
        { format.parse(Date.getText()); }
        catch (ParseException error)
        {
            System.out.println("[ERROR]@CreateDateDig/checkDateFormat");
            JOptionPane.showMessageDialog(null,
                    "Invalid date format!(Correct way: yyyy/MM/dd)\nIt is setted as today by default!", "DATE ERROR",
                    JOptionPane.ERROR_MESSAGE);
            Date.setText(today);//默认为当前日期
        }
    }
    private void checkCity() throws HeadlessException, SQLException{
        //检查输入日期格式
        if(Control.checkCity(SCity.getText())<=0 || Control.checkCity(TCity.getText())<=0)
        {
            JOptionPane.showMessageDialog(null,
                    "TIM - Non-existent city!", "INPUT ERROR", JOptionPane.ERROR_MESSAGE);
            SCity.setText("");
            TCity.setText("");
            validate();
        }
    }
    private void Purchase() throws SQLException
    {
        //购票

        TicketInfo selected_item = (TicketInfo)show_collection.get(selected_index);
        if(selected_index == -1)//未选中 
        {
            JOptionPane.showMessageDialog(null,
                    "You don't select any ticket yat", "FAIL TO BUY", JOptionPane.ERROR_MESSAGE, null);
        }
        else if(!Control.checkNum(selected_item.getID(), selected_item.getDate()))//无余票
        {
            JOptionPane.showMessageDialog(null,
                    "Insufficient ticket!", "FAIL TO BUY", JOptionPane.ERROR_MESSAGE, null);
        }
        else//正常购买
        {
            TIMGUIPurchaseDig purchaseDig = new TIMGUIPurchaseDig(selected_item);
            while(true)
            {
                int result = JOptionPane.showConfirmDialog(null, purchaseDig,
                        "TIM - Buy Ticket", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
                if (result == JOptionPane.OK_OPTION) //确认支付
                {
                    int change = Control.checkPayment(selected_item.getPrice(), purchaseDig.getPayment());
                    if(change >= 0)//支付成功
                    {
                        TIMGUIExchangeSuccessfully success = new TIMGUIExchangeSuccessfully(selected_item,
                                purchaseDig.getPayment(), change);
                        JOptionPane.showMessageDialog(null, success,
                                "TIM - Buy Ticket", JOptionPane.OK_OPTION, null);
                    	Control.purchase(selected_item.getID(), selected_item.getDate(), purchaseDig.getPayment(), String.valueOf(change));
                        System.out.println("test");
                    	refresh();
                        break;
                    }
                    else//支付失败
                    {
                        TIMGUIInsufficientPayment insufficientPayment =
                                new TIMGUIInsufficientPayment(selected_item.getPrice(), purchaseDig.getPayment());
                        JOptionPane.showMessageDialog(null, insufficientPayment,
                                "TIM - Buy Ticket", JOptionPane.OK_OPTION, null);
                        continue;
                    }
                }
                else if (result == JOptionPane.CANCEL_OPTION)//取消支付
                {
                    TIMGUIExchangeCancel exchangeCancel =
                            new TIMGUIExchangeCancel(purchaseDig.getPayment());
                    JOptionPane.showMessageDialog(null, exchangeCancel,
                            "TIM - Buy Ticket", JOptionPane.OK_OPTION, null);
                    break;
                }
            }
        }
    }
    private void refresh() throws SQLException
    {
//    	List.clear();
//        List = new TIMGUIList(Control, SCity.getText(), TCity.getText(), Date.getText());
    	List.refreshShowList(SCity.getText(), TCity.getText(), Date.getText());
        List.updateUI();
        validate();
    }
    public void actionPerformed(ActionEvent e){
        //侦听动作

        checkDateFormat();
        if(e.getSource() == Search)
			try {
				checkCity();
				refresh();
			} catch (HeadlessException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		else if(e.getSource() == Purchase)
        {
            selected_index = List.getList().getSelectedIndex();//获取选中事件序号
            try {
				Purchase();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            List.getList().clearSelection();
            selected_index = -1;
        }
    }
    public void focusGained(FocusEvent e) { checkDateFormat(); try {
		checkCity();
	} catch (HeadlessException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}}//焦点监听
    public void focusLost(FocusEvent e){ checkDateFormat(); try {
		checkCity();
	} catch (HeadlessException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}}//焦点丢失监听
    public void mousePressed(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
}
