package com.ysc;

/**
 * Created with IntelliJ IDEA.
 * User: yuyuyu
 * Date: 2022/4/12.
 * Time: 22:23.
 * To change this template use File | Settings | File Templates.
 * @author 86182
 */

/**
 *
 */
public class Account {
    /**
     * 成员变量，私有
     */
    private String cardId;//账户号
    private String userName;//姓名
    private String password;//密码
    private double money;//余额
    private double quotaMoney;//额度


    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getQuotaMoney() {
        return quotaMoney;
    }

    public void setQuotaMoney(double quotaMoney) {
        this.quotaMoney = quotaMoney;
    }
}
