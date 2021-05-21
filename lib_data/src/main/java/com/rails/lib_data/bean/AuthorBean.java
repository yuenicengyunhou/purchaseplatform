package com.rails.lib_data.bean;

import java.util.ArrayList;

/**
 * @author： sk_comic@163.com
 * @date: 2021/5/21
 */
public class AuthorBean {

    ArrayList<AuthorButtonBean> buttonBeans;
    ArrayList<AuthorMenuBean> menuBeans;

    public ArrayList<AuthorButtonBean> getButtonBeans() {
        return buttonBeans;
    }

    public void setButtonBeans(ArrayList<AuthorButtonBean> buttonBeans) {
        this.buttonBeans = buttonBeans;
    }

    public ArrayList<AuthorMenuBean> getMenuBeans() {
        return menuBeans;
    }

    public void setMenuBeans(ArrayList<AuthorMenuBean> menuBeans) {
        this.menuBeans = menuBeans;
    }
}
