package com.rails.lib_data.bean;

/**
 * 三级分类
 *
 * @author： sk_comic@163.com
 * @date: 2021/3/2
 */
public class CategorySubBean {

    //"name": "小五金及其他",
    //"fcid": 1001736
    private String name;
    private String url;
    private String fcid;


    public CategorySubBean(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg07file.tooopen.com%2Fimages%2F20180829%2Ftooopen_sl_213337333747611.jpg&refer=http%3A%2F%2Fimg07file.tooopen.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1617418238&t=04923941d0c4c8035e9df66e028df869";
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFcid() {
        return fcid;
    }

    public void setFcid(String fcid) {
        this.fcid = fcid;
    }
}
