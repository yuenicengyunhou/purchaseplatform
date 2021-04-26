var skuId,skuPic,skuPrice,skuName,shopName,inventory,successData,noAdd,specificationData,attrNameValueReaultVos;


layui.use(['rate','element','form','layer','admin','config','citypicker','util'], function(){
    var element = layui.element;
    var rate = layui.rate;
    var form=layui.form;
    var layer=layui.layer;
    var admin =layui.admin;
    var config =layui.config;
    var timeRender = layui.util;
    var domArr =[];
    var skuIdList=[];
    var accountType=window.accountType;

    var defaults = {
        s1: 'provid',
        s2: 'cityid',
        s3: 'areaid',
        v1: null,
        v2: null,
        v3: null
    };
    var domain = window.location.host;
    var params_ =getUrlParams();
    var pageNum = 1;
    var skuImgNum=0;
    var skuArrInfo=[];
    var skuInfoList = [];
    accountType = config.getKey().accountType;
    // 防疫列表接口
    var KYCidList = [];
    var isShowPrice = false; // true 显示价格 false 不显示价格
    function getCategory(callback){
        var kySessionList = JSON.parse(sessionStorage.getItem('kyCidList'));
        if (kySessionList && JSON.stringify(kySessionList) !== '[]') {
            KYCidList = kySessionList;
            callback();
        } else {
            admin.req(config.item_server + '/mall/frontcategory/getCategoryForShow',{ 'platformId': 20 },function (res) {
                if(res.code==0){
                    KYCidList = res.data;
                    sessionStorage.setItem('kyCidList', JSON.stringify(KYCidList));
                    callback();
                }
            },'GET');
        }
    }

    function initImgPositionBox() {

        $(".img-big").mousemove(function(e){
            var top=$("#bigImg").offset().top;
            var left=$("#bigImg").offset().left;
            var even = e || event; //兼容火狐浏览器
            var x = even.clientX;
            var y = even.clientY;
            // var obxX = even.clientX;
            var obxX = even.pageX;
            // if(obxX>269){
            //     obxX=269
            // }else if(obxX<87){
            //     obxX=87
            // }
            // var obxY = even.clientY;
            var obxY = even.pageY;
            // if(obxY>269){
            //     obxX=269
            // }else if(obxX<87){
            //     obxY=87
            // }
            $("#bigImg-big").css({
                top:"-"+((obxY-top-89)>172?172:(obxY-top-89))*2+"px",
                left:"-"+((obxX-left-88)>172?172:(obxX-left-88))*2+"px"
            });
            $(".maskBox").css({
                top:(obxY-top-89)>174?174:(obxY-top-89)<0?0:(obxY-top-89)+"px",
                left:(obxX-left-88)>177?177:(obxX-left-88)<0?0:(obxX-left-88)+"px"
            });
        });
    }


    if(params_.itemId) {
        var skuId = $("#skuId").val();
        //查询城市列表
        var provinceList = '';
        var cityList= []
        var provinceId='';
        var cityId = '';
        var countyId = '';
        var showAdd = true;
        var addressList = '';
        if (accountType === 1 || accountType === 2) { // 只有采购人才有收货地址
            getBuyerAddress();
        } else {
            $('.address_box .layui-input-block').attr('disabled', true).addClass('layui-disabled')
        }
        // 获取采购人配送地址
        function getBuyerAddress(){
            admin.req('/user-service/buyer/address/queryBuyerAddressListByBuyer',{platformId:20,"addressType":1},function (res) {
                res.data = res.data?res.data:[];
                addressList = res.data;
                getEnableAdds(addressList);
                res.data.forEach(function(ele,index){
                    if(ele.hasDefault == 1){
                        str ='<li class="address_item active" provinceCode= '+ele.provinceCode+ ' cityCode= '+ele.cityCode+' countryCode= '+ele.countryCode+' attachAddress= '+ele.fullAddress.replace(ele.attachAddress, '')+' ><em>'+ele.receiverName +'</em>  '+ele.fullAddress +'</li>';
                        $(".address").html(ele.fullAddress.replace(ele.attachAddress, ''));
                        $(".selected_address").html('<em>'+ ele.receiverName +'</em>'+ ele.fullAddress);
                        provinceId = ele.provinceCode;
                        cityId = ele.cityCode;
                        countyId = ele.countryCode;
                        cityList = [ele.fullAddress.replace(ele.attachAddress, '')];
                    }else{
                        str ='<li class="address_item" provinceCode= '+ele.provinceCode+ ' cityCode= '+ele.cityCode+' countryCode= '+ele.countryCode+' attachAddress= '+ele.fullAddress.replace(ele.attachAddress, '')+'><em>'+ele.receiverName +'</em>  '+ele.fullAddress +'</li>';
                    }
                    $('.address-list').append(str);
                });
            },"GET");
        }
        // 获取可用地址
        function getEnableAdds(addressList){
            admin.req('/user/mall/base/address/queryEnableAddsByParentCode',{platformId:20,"parentCode":0},function (resPro) {
                var str="";
                provinceList = resPro.data[0]
                resPro.data.forEach(function(ele,index){
                    if(index == 0){
                        if(getByteLen(ele.name) > 18){
                            str ='<span class="address_option_item active width50" code= '+ele.code+ ' level= '+ ele.level +'><em>'+ele.name+'</em></spn>';
                        }else{
                            str ='<span class="address_option_item active" code= '+ele.code+ ' level= '+ ele.level +'><em>'+ele.name+'</em></spn>';
                        }
                    }else{
                        if(getByteLen(ele.name) > 18) {
                            str ='<span class="address_option_item width50" code= '+ele.code+ ' level= '+ ele.level +'><em>'+ele.name+'</em></spn>';
                        }else{
                            str ='<span class="address_option_item" code= '+ele.code+ ' level= '+ ele.level +'><em>'+ele.name+'</em></spn>';
                        }
                    }
                    $('.address_option_content .option-content').eq(0).append(str);
                });
                if(addressList.length <= 0){
                    $(".often").hide();
                    var $new_address = $(".layui-collapse .layui-colla-item").eq(1).find("div").eq(0);
                    $new_address.addClass("layui-show");
                    cityList.push(resPro.data[0].name);
                    provinceId = resPro.data[0].code;
                    admin.req('/user/mall/base/address/queryEnableAddsByParentCode',{platformId:20,"parentCode":resPro.data[0].code},function (resCity) {
                        cityList.push(resCity.data[0].name);
                        cityId = resCity.data[0].code;
                        resCity.data.forEach(function(ele,index){
                            if(index == 0){
                                if(getByteLen(ele.name) > 18) {
                                    str ='<span class="address_option_item active width50" code= '+ele.code+ ' level= '+ ele.level +'>'+ele.name+'</spn>';
                                }else{
                                    str ='<span class="address_option_item active width50" code= '+ele.code+ ' level= '+ ele.level +'>'+ele.name+'</spn>';
                                }

                            }else{
                                if(getByteLen(ele.name) > 18) {
                                    str ='<span class="address_option_item width50" code= '+ele.code+ ' level= '+ ele.level +'>'+ele.name+'</spn>';
                                }else{
                                    str ='<span class="address_option_item width50" code= '+ele.code+ ' level= '+ ele.level +'>'+ele.name+'</spn>';
                                }
                            }
                            $('.address_option_content .option-content').eq(1).append(str);
                            $('.address_tab_item').eq(1).html(resCity.data[0].name);
                            $('.address_tab_item').eq(1).addClass('address_tab_show');
                        });
                        admin.req('/user/mall/base/address/queryEnableAddsByParentCode',{platformId:20,"parentCode":resCity.data[0].code},function (resArea) {
                            cityList.push(resArea.data[0].name);
                            countyId = resArea.data[0].code;
                            resArea.data.forEach(function(ele,index){
                                if(index == 0){
                                    if(getByteLen(ele.name) > 18) {
                                        str ='<span class="address_option_item active width50" code= '+ele.code+ ' level= '+ ele.level +'>'+ele.name+'</spn>';
                                    }else{
                                        str ='<span class="address_option_item active width50" code= '+ele.code+ ' level= '+ ele.level +'>'+ele.name+'</spn>';
                                    }
                                }else{
                                    if(getByteLen(ele.name) > 18) {
                                        str ='<span class="address_option_item width50" code= '+ele.code+ ' level= '+ ele.level +'>'+ele.name+'</spn>';
                                    }else{
                                        str ='<span class="address_option_item width50" code= '+ele.code+ ' level= '+ ele.level +'>'+ele.name+'</spn>';
                                    }
                                }
                                $('.address_option_content .option-content').eq(2).append(str);
                                $('.address_tab_item').eq(2).html(resArea.data[0].name);
                                $('.address_tab_item').eq(2).addClass('address_tab_show');
                            });
                            $(".address").html(cityList.join(" "));
                        },"GET");
                    },"GET");
                }
            },"GET");
        }

        //点击展示地区选择框
        $(document).on('click', '.layui-input-block', function(e) {
            var isDisabled = $(this).attr('disabled');
            e.stopPropagation();
            if (!isDisabled) {
                if(showAdd){
                    showAdd = false;
                    $('.goodsintro-address_content').show();
                }else{
                    showAdd = true;
                    $('.goodsintro-address_content').hide();
                }
            }
        })

        /*//点击其他位置关闭地区选择框
        $(document).on('click', function(e) {
            $('.goodsintro-address_content').hide();
        })*/
        //点击区域选择地区
        $(document).on('click', '.address_option_item', function(e) {
            e.stopPropagation();
            var $this = $(this);
            var level = $this.attr('level');
            //tab选择框改变
            $('.address_tab_item').eq(level-1).html($this.find("em").html());
            $('.address_tab_item').eq(level).html('请选择');
            $('.address_tab_item').eq(level).addClass('address_tab_show');
            // $this.siblings().removeClass("active");
            $this.addClass("active");
            if(level==1){
                $('.address_tab_item').eq(2).html('请选择');
                $('.address_tab_item').eq(2).removeClass('address_tab_show');
                provinceId = $this.attr('code');
                cityList.splice(0,1,$this.find("em").html());
            }
            if(level<3){
                $('.address_tab_item').eq(level-1).removeClass('address_tab_choose');
                $('.address_tab_item').eq(level).addClass('address_tab_choose');
                //内容区域改变
                var code = $this.attr('code');
                cityId = $this.attr('code');
                cityList.splice(1,1,$this.find("em").html());
                admin.req('/user/mall/base/address/queryEnableAddsByParentCode',{platformId:20,"parentCode":code},function (res) {
                    if (res.code == 0 && res.data && res.data.length>0) {
                        var address = res.data;
                        var addressListHtml = '';
                        for(var j=0;j<address.length;j++){
                            if(getByteLen(address[j].name) > 18) {
                                // addressListHtml += `<span class="address_option_item width50" code=${address[j].code} level=${address[j].level}><em>${address[j].name}</em></span>`
                                addressListHtml += '<span class="address_option_item width50" code='
                                    .concat(address[j].code, " level=")
                                    .concat(address[j].level, "><em>")
                                    .concat(address[j].name, "</em></span>");
                            }else{
                                // addressListHtml += `<span class="address_option_item" code=${address[j].code} level=${address[j].level}><em>${address[j].name}</em></span>`
                                addressListHtml += '<span class="address_option_item" code='
                                    .concat(address[j].code, " level=")
                                    .concat(address[j].level, "><em>")
                                    .concat(address[j].name, "</em></span>");
                            }
                        };
                        $('.address_option_content').eq(level-1).removeClass('address_option_show');
                        $('.address_option_content .option-content').eq(level).html(addressListHtml);
                        $('.address_option_content').eq(level).addClass('address_option_show');

                    }
                },"GET");
            } else {
                $('.goodsintro-address_content').hide();
                $('.goodsintro-address_head').removeClass('address_head_click');
                $('.ico-arr').removeClass('ico-arr-active');
                // var addressText = `<span>${$('.address_tab_item').eq(0).html()}</span><span>${$('.address_tab_item').eq(1).html()}</span><span>${$('.address_tab_item').eq(2).html()}</span>`;
                var addressText = "<span>"
                    .concat(
                        $(".address_tab_item")
                            .eq(0)
                            .html(),
                        "</span><span>"
                    )
                    .concat(
                        $(".address_tab_item")
                            .eq(1)
                            .html(),
                        "</span><span>"
                    )
                    .concat(
                        $(".address_tab_item")
                            .eq(2)
                            .html(),
                        "</span>"
                    );
                $('.address_head_text').html(addressText);
                //改变top上的省
                //$('.city .city-name').html(mainAreaName);

                countyId = $this.attr('code');
                cityList.splice(2,1,$this.find("em").html());
                $(".address").html(cityList.join(" "));
                getGoods();
                showAdd = true;
            }

        });

        //点击地区tab切换市级
        $(document).on('click', '.address_tab_item', function(e) {
            e.stopPropagation();
            var $this = $(this);
            var level = $(this).attr('level');
            //tab选择框改变
            for(var j=0;j<$('.address_tab_item').length;j++){
                $('.address_tab_item').eq(j).removeClass('address_tab_choose');
            }
            $this.removeClass('address_hidden');
            $this.addClass('address_tab_choose');
            //内容区域改变
            for(var k=0;k<$('.address_option_content').length;k++){
                if(level-1 == k){
                    $('.address_option_content').eq(k).addClass('address_option_show');
                } else {
                    $('.address_option_content').eq(k).removeClass('address_option_show');
                }
            }
        })
        //点击常用地址切换
        $(document).on('click', '.address_item', function(e) {
            e.stopPropagation();
            var $this = $(this);
            var level = $(this).attr('level');
            $this.siblings().removeClass("active");
            $this.addClass("active");
            provinceId = $this.attr('provinceCode');
            cityId = $this.attr('cityCode');
            countyId = $this.attr('countryCode');
            cityList = [$this.attr('attachAddress')];
            getGoods();
            $(".address").html(cityList);
            $('.goodsintro-address_content').hide();
            $(".selected_address").html($this.html());
            showAdd = true;
        })

        //点击更多参数
        $(document).on('click', '.more-par', function(e) {
            e.stopPropagation();
            $('#road-tab .layui-tab-title li').eq(1).click();
        });
        //监听折叠
        element.on('collapse(test)', function(data){
            var $new_address = $(".layui-collapse .layui-colla-item").eq(1).find("div").eq(0);
            if(data.title[0].className.indexOf("often-address")>0){
                if(data.show){
                    $(".selected_address").hide();
                    $new_address.removeClass("layui-show");
                }else{
                    $(".selected_address").show();
                    $new_address .addClass("layui-show");
                }
            }else{
                if(data.show){
                    $(".selected_address").show();
                    $(".often").find("div").eq(0).removeClass("layui-show");
                }else{
                    $(".selected_address").hide();
                    $(".often").find("div").eq(0).addClass("layui-show");
                }
            }

            /*if( $new_address.attr("class").indexOf("layui-show") <0){
                $(".often").find("div").eq(0).removeClass("layui-show");
                $new_address .addClass("layui-show");
            }else{
                $(".often").find("div").eq(0).addClass("layui-show");
                $new_address.removeClass("layui-show");
            }*/
        });
        //返回字符串长度
        function getByteLen(val) {
            var len = 0;
            for (var i = 0; i < val.length; i++) {
                var a = val.charAt(i);
                if (a.match(/[^\x00-\xff]/ig) != null)
                {
                    len += 2;
                }
                else
                {
                    len += 1;
                }
            }
            return len;
        }

        function getSkuId(){
            var itemSkuInfoList =  window.itemSkuInfoList;
            var findStr = [];
            var _skuid = "";
            $(".color").find("div").each(function(){
                var attrValueId = $(this).find("li.active").attr("skuId");
                findStr[findStr.length]= ":"+attrValueId+";";
            });

            for(var i=0; i<itemSkuInfoList.length; i++){
                var found = 0;
                for(var j=0;j<findStr.length; j++){
                    if(itemSkuInfoList[i].attributes.indexOf(findStr[j]) != -1){
                        found++;
                    }
                }
                if(found === findStr.length){
                    _skuid = itemSkuInfoList[i].id;
                    break;
                }
            }
            return _skuid;
        }
        function getGoods(){
            var params = {};
            params.supplierId = $("#shopId").val();
            params.provinceId = provinceId;
            params.cityId = cityId;
            params.countyId = countyId;
            params.address = cityList.join("");
            // params.querySkuSale = {skuNum:1,shopId: $("#shopId").val(),skuId:$("#skuId").val()};
            params.skuNum =1;
            params.skuNumList =[1];
            params.shopId = $("#shopId").val();
            params.skuId = $("#skuId").val();
            params.skuIdList = skuIdList;
            admin.req(config.item_server+'/mall/search/querySkuSaleStocks',params,function (res) {
                // console.log('nnnnnnnnnnnnnnnnnnn',res);
                if(res.code == 0){
                    // disabledBtn(res.data);
                    if(res.data == null || res.data.length==0 || res.data[0].saleState == 0||res.data[0].skuStock==0){
                        noAdd = true;
                        $(".inStock").html('无货');
                        $(".num input").addClass("add-input-no");
                        $(".num span").addClass("no-add");
                        $(".add-button button").addClass("add-button-no");
                        $(".addcar").addClass("add-button-no");
                    }else{
                        noAdd = false;
                        $(".inStock").html('有货');
                        $(".num input").removeClass("add-input-no");
                        $(".num span").removeClass("no-add");
                        $(".add-button button").removeClass("add-button-no");
                        $(".addcar").removeClass("add-button-no");
                    }
                }
            },"POST");
        }
        function disabledBtn(data) {
            for(var i=0; i<data.length; i++){
                if(data[i]["saleState"]==0){
                    $("li[skuid="+data[i]["saleState"]["skuId"]+"]").removeClass("active");
                    $("li[skuid="+data[i]["saleState"]["skuId"]+"]").css("opcity","0.2")
                }else {
                    $("li[skuid="+data[i]["saleState"]["skuId"]+"]").css("opcity","1")
                }
            }
        }
        function noAddCar(){
            $(".num input").addClass("add-input-no");
            $(".num span").addClass("no-add");
            $(".add-button button").addClass("add-button-no");
            $(".addcar").addClass("add-button-no");
        }
        function okAddCar(){
            $(".num input").removeClass("add-input-no");
            $(".num span").removeClass("no-add");
            $(".add-button button").removeClass("add-button-no");
            $(".addcar").removeClass("add-button-no");
        }
        function returnFloat(value){
            // var value=Math.round(parseFloat(value)*100)/100;
            value = Math.round(parseFloat(value)*100)/100;
            var xsd=value.toString().split(".");
            if(xsd.length==1){
                value=value.toString()+".00";
                return value;
            }
            if(xsd.length>1){
                if(xsd[1].length<2){
                    value=value.toString()+"0";
                }
                return value;
            }
        }
        //查詢熱銷商品
        admin.req(config.item_server+ '/mall/search/querySaleHotItem', {platformId:20,itemId:params_.itemId}, function (res) {
            if (res.code == 0) {
                var length = res.data.length;
                if(length >3){
                    length = 3;
                }
                var hotSaleAttrs = "";
                for (var i = 0; i<length; i++) {
                    hotSaleAttrs += '<li class="hotsale-li">' +
                        '<div class="hotsale-img">' +
                        '<a href='+"detail?itemId="+res.data[i].itemId+' target="_blank">' +
                        '<img src='+res.data[i].pictureUrl+'></a><br>'+
                        '<span><a href='+"detail"+"?itemId="+res.data[i].itemId+' target="_blank">'+res.data[i].itemName+ '</a></span>' +
                        '</div>' +
                        '<div class="hotsale-font">' +
                        //    '<p>'+res.data[i].minPrice+"元"+'</p>' +
                        '<span><em>￥</em>'+ '<span class="minPrice">' + returnFloat(res.data[i].minPrice) + '</span>'+'</span>' +
                        '<span class="span-right">'+res.data[i].skuCount+"种商品"+'</span>' +
                        '</div>' +
                        // '<div class="hot-top-num">'+(i+1)+'</div>'+
                        '</li>';
                }
                $('.hotsale-ul').append(hotSaleAttrs);
                getCategory(function callback() { // 热销商品
                    var flag = 0;
                    res.data.forEach(function(item, index){
                        if (KYCidList.indexOf(item.cid*1) !== -1) {
                            if(accountType == 2) {
                                $(".hotsale-font .minPrice").html("").addClass('noPrice');
                            }
                            flag = 1;
                        } else {}
                    });
                    if(flag == 1) {
                        $(".hotsale-img a").append('<i class="kyLogo"></i>');
                    }
                });
            }
        }, 'GET');

        //查詢商品基本信息
        admin.req(config.item_server+ '/mall/search/queryNormalItemDetails', {platformId:20,itemId:params_.itemId,areaId:-1}, function (res) {
            if (res.code == 0 && res.data) {
                specificationData = res.data.itemPublishVo;//获取规格属性
                getShopFreightAmount(specificationData.shopId)
                var skuArr=res.data.itemSkuInfoList;
                skuInfoList = skuArr;
                // 当前时间
                var nowTime = new Date().Format("yyyy-MM-dd hh:mm:ss");
                var date1 = new Date();
                var data2 = new Date(date1);
                data2.setDate(date1.getDate() - 30);
                // 30天后时间
                var oneMonthTime = data2.Format("yyyy-MM-dd hh:mm:ss");
                var data=[];
                for(var i=0;i<skuArr.length;i++){
                    var upTime = '',
                        skuItem = skuArr[i]
                    if (skuItem.upTime && skuItem.upTime !== null) {
                        upTime = timeRender.toDateString(skuItem.upTime);
                    }
                    if (upTime && caluDiffdays(upTime, oneMonthTime) <= 0 && caluDiffdays(nowTime, upTime) <= 30 ){
                        skuItem.isNewGoods = 'yes';
                    }
                    data.push({attributes:skuArr[i].attributes,attributesName:skuArr[i].attributesName,specAttributes:skuArr[i].specAttributes,materialCode:skuArr[i].materialCode})
                }
                skuInfoList = skuArr;
                var arr1=[],arr2=[];
                for(var i=0;i<data.length;i++){
                    arr1.push(data[i].attributes.split(';'));
                }
                for(var i=0;i<arr1.length;i++){
                    var str='';
                    for(var j=0;j<arr1[i].length;j++){
                        if(arr1[i][j].split(':')[1]){
                            str+=arr1[i][j].split(':')[1]+',';
                        }
                    }
                    var strArr1=str.split(',');
                    strArr1.splice(strArr1.length-1,1);
                    arr2.push(strArr1.join(','));
                }
                for(var i=0;i<data.length;i++){
                    data[i].attributes=arr2[i]
                }
                skuArrInfo=data;
                $("#skuId").val(res.data.itemSkuInfoList[0].id);
                $("#cid").val(res.data.itemPublishVo.cid);
                if(res.data.itemPublishVo.frontCategoryVoList){
                    for (var i = 0; i < res.data.itemPublishVo.frontCategoryVoList.length; i++) {
                        $("#fullCategory").append('<a href="/mall-view/product/search?businessType=1&cid='+ res.data.itemPublishVo.frontCategoryVoList[i].fcid+'" >' + res.data.itemPublishVo.frontCategoryVoList[i].fcname + ' > </a>');
                    }
                    var str=$('#fullCategory').find('a').eq(res.data.itemPublishVo.frontCategoryVoList.length).html()
                    $('#fullCategory').find('a').eq(res.data.itemPublishVo.frontCategoryVoList.length).html(str.substring(0,str.length-5));
                    $('#fullCategory').find('a').eq(res.data.itemPublishVo.frontCategoryVoList.length).css('color','#999')
                }

                // $("#description").text(res.data.itemPublishVo.describeUrl)
                $("#brandName").text(res.data.itemPublishVo.brandName);
                // $("#code").text(res.data.itemPublishVo.code);
                initImgPositionBox()
                //$("#bigImg").attr('src', res.data.itemPictureVoList[0].pictureUrl);
                //信用评级只展示B,C,D 其余不展示
                if(res.data.itemPublishVo.creditLevel == 'B'){
                    $(".shop-name").html("<a href='/mall-view/shop?shopId="+res.data.itemPublishVo.shopId+"\'>"+res.data.itemPublishVo.shopName+" <img class='creditRating' src='/mall-view/img/B.png'><span class='creditColor'>风险较低</span></a>");
                }else if(res.data.itemPublishVo.creditLevel == 'C'){
                    $(".shop-name").html("<a href='/mall-view/shop?shopId="+res.data.itemPublishVo.shopId+"\'>"+res.data.itemPublishVo.shopName+" <img class='creditRating' src='/mall-view/img/C.png'><span class='creditColor'>风险较高</span></a>");
                }else if(res.data.itemPublishVo.creditLevel == 'D'){
                    $(".shop-name").html("<a href='/mall-view/shop?shopId="+res.data.itemPublishVo.shopId+"\'>"+res.data.itemPublishVo.shopName+" <img class='creditRating' src='/mall-view/img/D.png'><span class='creditColor'>风险极高</span></a>");
                }else{
                    $(".shop-name").html("<a href='/mall-view/shop?shopId="+res.data.itemPublishVo.shopId+"\'>"+res.data.itemPublishVo.shopName+"</a>");
                }
                //商品信息 start
                $('#describeUrl .des-parameter #parameter-brand li').html('品牌：'+res.data.itemPublishVo.brandName);

                var parameterHtml = '';
                var origin = res.data.itemPublishVo.origin == null ? '暂无' : res.data.itemPublishVo.origin
                parameterHtml +='<li title="'+ res.data.itemPublishVo.itemName +'">商品名称：'+ res.data.itemPublishVo.itemName +'</li>';
                parameterHtml +='<li title="'+ res.data.itemPublishVo.id +'">商品编码：'+ res.data.itemPublishVo.id +'</li>';
                parameterHtml +='<li title="'+ origin +'">商品产地：'+ origin +'</li>';
                $('.des-parameter #parameter-list').html(parameterHtml)
                $("#describeUrl .picture").html(res.data.itemPublishVo.describeUrl)
                //商品信息 end

                // 推荐单位 start
                var recomendedData;
                if(res.data && res.data.itemPublishVo.supplierInfoImportData) {
                    recomendedData = res.data.itemPublishVo.supplierInfoImportData;
                    $(".recommendOrg").html(recomendedData.recommendOrg ? recomendedData.recommendOrg : '暂无数据'); // 推荐单位
                    $(".bindOrgName").html(recomendedData.bindOrgName ? recomendedData.bindOrgName : '暂无数据'); // 货运单位
                    $(".accountName").html(recomendedData.accountName ? recomendedData.accountName :'暂无数据'); // 货运客户经理
                } else {
                    $(".recommendOrg").html('暂无数据'); // 推荐单位
                    $(".bindOrgName").html('暂无数据'); // 货运单位
                    $(".accountName").html('暂无数据'); // 货运客户经理
                }
                // 推荐单位end

                // 售后服务 start
                if(res.data.itemPublishVo.itemAfterSaleVo){
                    var itemAfterSaleVo = res.data.itemPublishVo.itemAfterSaleVo;
                    if(itemAfterSaleVo.refundService==1){
                        $("#origin").append('<p class="title"><em></em>售后退货说明</p><p class="content">特殊商品不允许退货</p>');
                    }else{
                        if(itemAfterSaleVo.refundDuration){
                            var html = '<p class="title"><em></em>售后退货说明</p><p class="content">确认收货后'+ itemAfterSaleVo.refundDuration +'日内出现质量问题可申请退货。</p>';
                            $("#origin").append(html);
                        }
                    }
                    if(itemAfterSaleVo.changeService==1){
                        $("#origin").append('<p class="title"><em></em>售后换货说明</p><p class="content">特殊商品，一经签收不予换货</p>');
                    }else{
                        if(itemAfterSaleVo.changeDuration){
                            var html = '<p class="title"><em></em>售后换货说明</p><p class="content">确认收货后'+ itemAfterSaleVo.changeDuration +'日内出现质量问题可申请换货。</p>';
                            $("#origin").append(html);
                        }
                    }
                    if(itemAfterSaleVo.repaireDuration){
                        $("#origin").append('<p class="title"><em></em>售后质保说明</p><p class="content">确认收货后'+itemAfterSaleVo.repaireDuration+'月内出现质量问题可申请质保。</p>');
                    }
                    if(itemAfterSaleVo.specialDesc){
                        $("#origin").append('<p class="title"><em></em>特殊说明</p><p class="content">'+itemAfterSaleVo.specialDesc+'</p>');
                    }
                }
                 // 售后服务 end

                $("#shopId").val(res.data.itemPublishVo.shopId);
                $("#attributes").val(res.data.itemSkuInfoList[0].attributes);
                $("#arrNames").val(res.data.itemPublishVo.attrNameArray);
                if($("#describeUrl .picture div").attr("cssurl")){
                    var link = document.createElement("link");
                    link.rel = "stylesheet";
                    link.type = "text/css";
                    link.href = $("#describeUrl .picture div").attr("cssurl");
                    var head = document.getElementsByTagName("head")[0];
                    head.appendChild(link);
                }
                if(res.data.itemPublishVo.attrNameArray && res.data.itemPublishVo.attrNameArray.length >= 1){
                    //回显商品销售属性
                    salesAttrRender(res.data);
                }else{
                    $("#skuId").val(res.data.itemSkuInfoList[0].id);
                    getPrice(res.data.itemSkuInfoList[0].id);
                }
                var isfirstLoad = false;
                //请求url参数带有skuId
                if(params_.skuId) {
                    //遍历所有的销售属性和销售属性子项匹配
                    for (var i = 0; i < res.data.itemSkuInfoList.length; i++) {
                        var attributes = res.data.itemSkuInfoList[i].attributes;
                        var attributesName = res.data.itemSkuInfoList[i].attributesName;
                        if(attributes&&attributesName&&params_.skuId==res.data.itemSkuInfoList[i].id){
                            // var attributesArray = attributes.replace(/:/g, ";").split(";");
                            // $('.color ul li').each(function(i,n){
                            //     attributesArray.forEach(v=>{
                            //         if(v==n.value) {
                            //             $(this).addClass("active").siblings("li").removeClass("active");
                            //             var arrName =$(this).parent().siblings("span")[0].innerText;
                            //             if(!isfirstLoad){
                            //                 isfirstLoad = $(this);
                            //             }
                            //             //querySkuInfo(v,arrName);
                            //         }
                            //     });
                            // });
                            var attributesArray = attributes.replace(/:/g, ";").split(";");
                            $(".color ul li").each(function(i, n) {
                                var _this = this;

                                attributesArray.forEach(function(v) {
                                    if (v == n.value) {
                                        $(_this)
                                            .addClass("active")
                                            .siblings("li")
                                            .removeClass("active");
                                        var arrName = $(_this)
                                            .parent()
                                            .siblings("span")[0].innerText;

                                        if (!isfirstLoad) {
                                            isfirstLoad = $(_this);
                                        } //querySkuInfo(v,arrName);
                                    }
                                });
                            });
                        }
                    }
                    setTimeout(function () {
                        if(isfirstLoad){
                            isfirstLoad.click();
                        }
                    })
                    getPrice(parseInt(params_.skuId));
                }else{
                    var attributes = res.data.itemSkuInfoList[0].attributes;
                    var attributesName = res.data.itemSkuInfoList[0].attributesName;
                    var attributesArray = attributes.replace(/:/g, ";").split(";");
                    // $('.color ul li').each(function(i,n){
                    //     attributesArray.forEach(v=>{
                    //         if(v==n.value) {
                    //         $(this).addClass("active").siblings("li").removeClass("active");
                    //             var arrName =$(this).parent().siblings("span")[0].innerText;
                    //             //querySkuInfo(v,arrName);
                    //         }
                    //     });
                    // });
                    $(".color ul li").each(function(i, n) {
                        var _this = this;

                        attributesArray.forEach(function(v) {
                            if (v == n.value) {
                                $(_this)
                                    .addClass("active")
                                    .siblings("li")
                                    .removeClass("active");
                                var arrName = $(_this)
                                    .parent()
                                    .siblings("span")[0].innerText; //querySkuInfo(v,arrName);
                            }
                        });
                    });
                    getPrice(res.data.itemSkuInfoList[0].id);
                }

                initSelect();
                $(".smallImg li img").on('click', function () {
                    var src = $(this).attr("src");
                    $(this).parents(".left").find(".img-big img").attr("src", src);
                    $(this).parent("li").addClass("active").siblings("li").removeClass("active");
                });

                //通过销售属性确定sku接口
                $(".color ul li").unbind('click').on('click', function () {
                    if($(this).attr("style")=="opacity: 0.2"){
                        return false;
                    }
                    judge = 0;
                    $('.add-input').val(1);
                    var indexCount =  $(this).parent().attr("indexCount");//规格分类总数
                    var index = parseInt($(this).parent().attr("index"))+1;//第几个规格分类
                    var parentIds = $(this).attr("skuId");
                    $(this).addClass("active").siblings("li").removeClass("active");
                    if(indexCount == 1){        //只有一个规格
                        $("#selectOne").val($(this).attr("skuId"));
                        var arrName = $(this).parent().find("span").text();
                        getItemSkuInfoListOne(parentIds,arrName);
                    }
                    if(indexCount == 2){        //有两个规格
                        if(index < indexCount){
                            $("#selectOne").val($(this).attr("skuId"));
                            var arrName = $(this).parent().find("span").text();
                            getItemSkuInfoListTwo(parentIds,arrName);
                        }
                        if(index == indexCount){
                            $("#selectTwo").val($(this).attr("skuId"));
                            var arrName =$(this).parent().find("span").text();
                            var parentIds = $("#selectOne").val().toString();
                            var clickId = $(this).attr("skuId").toString();
                            var arrNames = $(this).parent().parent().find("span").eq(0).text();
                            getItemSkuInfoListTwoTwo(parentIds,arrName,clickId,arrNames);
                        }
                    }
                    if(indexCount == 3){       //有三个规格
                        if($(this).parent().attr("index") == 0){
                            $("#selectOne").val($(this).attr("skuId"));
                            var arrName = $(this).parent().find("span").text();
                            getItemSkuInfoListThree_one($("#selectOne").val(),arrName);
                            var arrName_ = $(this).parent().parent().find("span").eq(2).text();
                            var parentIds_ = [$("#selectOne").val(),$("#selectTwo").val()];
                            getItemSkuInfoListThree_two(parentIds_,arrName_);
                        }
                        if($(this).parent().attr("index") == 1){
                            var arrNames_ = $(this).parent().parent().find("span").eq(0).text();
                            var arrName_ = $(this).parent().parent().find("span").eq(1).text();
                            var clickId = $(this).attr("skuId");
                            $("#selectTwo").val($(this).attr("skuId"));
                            getItemSkuInfoListThree_three(arrName_,clickId,arrNames_,2);
                            var parentIds = [$("#selectOne").val(),$("#selectTwo").val()];
                            var arrName = $(this).parent().parent().find("span").eq(2).text();
                            var clickId_ = $("#selectThree").val();
                            var arrNames = [arrNames_,arrName_];
                            var selectId = $("#selectThree").val();
                            getItemSkuInfoListThree_four(parentIds,arrName,clickId_,clickId,arrName_,arrNames,selectId,3);
                        }
                        if($(this).parent().attr("index") == 2){
                            var arrNames_ = $(this).parent().parent().find("span").eq(0).text();
                            var arrName_ = $(this).parent().parent().find("span").eq(2).text();
                            var clickId = $(this).attr("skuId");
                            $("#selectThree").val($(this).attr("skuId"));
                            getItemSkuInfoListThree_three(arrName_,clickId,arrNames_,3);
                            var parentIds = [$("#selectOne").val(),$("#selectTwo").val()];
                            var arrName = $(this).parent().parent().find("span").eq(1).text();
                            var clickId_ = $("#selectThree").val();
                            var arrNames = [arrNames_,arrName_];
                            var selectId = $("#selectThree").val();
                            getItemSkuInfoListThree_four(parentIds,arrName,clickId_,clickId,arrName_,arrNames,selectId,2);
                        }
                    }
                    if(indexCount == 4){       //有四个规格
                        if($(this).parent().attr("index") == 0){
                            $("#selectOne").val($(this).attr("skuId"));
                            var arrName = $(this).parent().find("span").text();
                            getItemSkuInfoListThree_one($("#selectOne").val(),arrName);
                            var arrName_ = $(this).parent().parent().find("span").eq(0).text();
                            var parentIds_ = [$("#selectTwo").val(),$("#selectThree").val(),$("#selectFour").val()];
                            getItemSkuInfoListFour_two(parentIds_,arrName_);
                        }
                        if($(this).parent().attr("index") == 1){
                            var arrNames_ = $(this).parent().parent().find("span").eq(0).text();
                            var arrName_ = $(this).parent().parent().find("span").eq(1).text();
                            var arrNameFour_ = $(this).parent().parent().find("span").eq(3).text();
                            var clickId = $(this).attr("skuId");
                            $("#selectTwo").val($(this).attr("skuId"));
                            getItemSkuInfoListThree_three(arrName_,clickId,arrNames_,2);
                            var parentIds = [$("#selectOne").val(),$("#selectThree").val(),$("#selectFour").val()];
                            var arrName = $(this).parent().parent().find("span").eq(2).text();
                            var clickId_ = $("#selectTwo").val();
                            var arrNames = [arrNames_,arrName_,arrNameFour_];
                            var selectId = $("#selectTwo").val();
                            getItemSkuInfoListFour_four(parentIds,arrName,clickId_,clickId,arrName_,arrNames,selectId,1);
                        }
                        if($(this).parent().attr("index") == 2){
                            var arrNames_ = $(this).parent().parent().find("span").eq(0).text();
                            var arrName_ = $(this).parent().parent().find("span").eq(2).text();
                            var arrName_four = $(this).parent().parent().find("span").eq(3).text();
                            var clickId = $(this).attr("skuId");
                            $("#selectThree").val($(this).attr("skuId"));
                            getItemSkuInfoListThree_three(arrName_,clickId,arrNames_,3);
                            var parentIds = [$("#selectOne").val(),$("#selectTwo").val(),$("#selectFour").val()];
                            var arrName = $(this).parent().parent().find("span").eq(2).text();
                            var clickId_ = $("#selectThree").val();
                            var arrNames = [arrNames_,arrName_,arrName_four];
                            var selectId = $("#selectThree").val();
                            getItemSkuInfoListFour_four(parentIds,arrName,clickId_,clickId,arrName_,arrNames,selectId,2);
                        }
                        if($(this).parent().attr("index") == 3){
                            var arrNames_ = $(this).parent().parent().find("span").eq(0).text();
                            var arrName_ = $(this).parent().parent().find("span").eq(1).text();
                            var arrName_four = $(this).parent().parent().find("span").eq(2).text();
                            var clickId = $(this).attr("skuId");
                            $("#selectFour").val($(this).attr("skuId"));
                            getItemSkuInfoListThree_three(arrName_,clickId,arrNames_,4);
                            var parentIds = [$("#selectOne").val(),$("#selectTwo").val(),$("#selectThree").val()];
                            var arrName = $(this).parent().parent().find("span").eq(1).text();
                            var clickId_ = $("#selectFour").val();
                            var arrNames = [arrNames_,arrName_,arrName_four];
                            var selectId = $("#selectFour").val();
                            getItemSkuInfoListFour_four(parentIds,arrName,clickId_,clickId,arrName_,arrNames,selectId,3);
                        }
                    }
                    return;

                });

                getCollection();
                setBrowseRecord();
                getGoods();
                insertVisitRecord();
            } else {
                layer.msg(res.msg ? res.msg : res.message, {time: 2000}, function(){
                    window.location.href = '/mall-view/'
                });
            }
        }, 'GET');
    }
    $(".add-input").on('blur',function (e) {
        var target = $(e.target),
            text = $.trim(target.val());
        if (text === '' || text === '0') {
            target.val('1');
        }
        if (!/^\d+$/.test(text)) {
            target.val('1');
        }
    });

    /**
     * 页面上方销售属性处理
     * @param data
     */
    function salesAttrRender(data){
        //通过itemSkuInfoList每个sku的attributesName的值进行维护，其中单spu的attributesName的值为null，需特殊处理

        var salesAttributeLst = {};
        //数据预处理，组装 salesAttributeLst
        $.each(data.itemSkuInfoList,function(index, sku){
            //单spu时，sku无销售属性的切换框
            if(sku.attributesName != null){
                //解析attributesName内容为：CPU型号:Interl i7;机身颜色:黑色;内存容量:8G;
                var salesAttrName = sku.attributesName.split(";");
                // attributesName和attributes等长
                var salesAttrValue = sku.attributes.split(";"); //29137:134332;28999:134066;29000:134073;

                window.itemSkuInfoList = data.itemSkuInfoList;

                for(var i = 0;i<salesAttrName.length; i++){
                    //最后一个为""，或者存在为字符串null的情况，直接跳过
                    if("" == salesAttrName[i] || "null" == salesAttrName[i]) {
                        continue;
                    }

                    // var salesName = salesAttrName[i].split(":");
                    var salesName1 = salesAttrName[i].slice(0,salesAttrName[i].indexOf(':'));
                    var salesName2 = salesAttrName[i].slice(salesAttrName[i].indexOf(':')+1);
                    var attrName = salesName1;
                    var valueName = salesName2;

                    if(salesAttrValue.length > 0) {
                        var salesIds = salesAttrValue[i].split(":");
                        // var attrId = salesIds[0];
                        var valueId = salesIds[1];
                    }


                    //组装json为attrName:[{vId:aaa vName:bbb}]
                    if(salesAttributeLst[attrName] == null){
                        salesAttributeLst[attrName]={};
                    }
                    // 在数组末尾增加一个value的码值
                    salesAttributeLst[attrName][valueName] = valueId;
                }
            }
        });
        //完成 salesAttributeLst 的组装，结构为{"属性名A":{"属性值x的名称":"属性值x的ID","属性值y的名称":"属性值y的ID"}}
        //example:"{"颜色":{"土豪金":"85712","白色":"85713"},"表盘厚度":{"3mm":"85709","7mm":"85710"},"手表种类":{"男":"85708","女":"85711"}}"

        //render
        var renderHtml = "";
        for (var i = 0; i < data.itemPublishVo.attrNameArray.length; i++) {
            var attrName = data.itemPublishVo.attrNameArray[i];

            if(null == salesAttributeLst[attrName]){
                continue;
            }

            renderHtml += '<div>'+'<span class="attr-name">' + attrName + '</span>' +'<ul index="'+ i + '" indexCount="'+ data.itemPublishVo.attrNameArray.length+ '">';
            //jquery遍历object，此时key为valueName，Value为valueId
            // console.log("?????????????????????????????",salesAttributeLst);
            var arr = []
            for(var key in salesAttributeLst){
                for(var son in salesAttributeLst[key]){
                    arr.push(salesAttributeLst[key][son])
                }
            }
            skuIdList = arr.join(",");
            // console.log(skuIdList);
            $.each(salesAttributeLst[attrName], function(valueName,valueId){
                renderHtml += '<li skuId="'+ valueId +'"  value="' + valueId + '" title="'+ valueName +'">' + valueName + '<i></i></li>';
            })
            renderHtml += '</ul></div>';
        }

        $('.color').append(renderHtml);
    };

    function getHtml(arrName,data){
        var tab_ = '';
        for(var i=0;i<arrName.length;i++){
            tab_ += '<td>'+ (dataTypeConvert(data)[arrName[i]] || "--")+'</td>';
        }
        return tab_;
    }
    function getHtml_(arrName,data){
        var tab_ = '';
        for(var i=0;i<arrName.length;i++){
            tab_ += '<td>'+ (dataTypeConvert(data)[arrName[i]] || "--")+'</td>';
        }
        return tab_;
    }
    function dataTypeConvert(str) {
        var obj = {};
        if(str !== '' && str !==null ) {
            var arr = str.split(';');
            arr.forEach(function (i, index) {
                var tempArr = i.split(':');
                obj[tempArr[0]] = tempArr[1];
            })
        }
        return obj;
    }
    function searchSaleList(params,arr,fn){
        params['skuIdList']=skuIdList;
        admin.req(config.item_server+'/mall/search/querySkuSaleStocksList',params,function (result) {
            if(result.code == 0){
                for(var i=0;i<arr.length;i++){
                    arr[i].saleState=result.data[0].saleState;
                }
                fn(true,arr)
            }else{
                fn(false)
            }
        },"POST");
    }
    var judge = 0;

    function getCommodite(skuId,cid,pageNum){
        if(judge==1&&judge==pageNum){
            return
        }else {
            judge=pageNum
        }
        admin.req(config.item_server+ '/mall/search/querySkuSimilarCommodite', {platformId:20,pageSize:3,pageNum:pageNum,skuId:skuId,cid:cid}, function (res) {
            // (">>>",res);
            if (res.code == 0 && res.data.length > 0) {
                var arr=[],
                    skuListArr = [],
                    params = {};
                // 当前时间
                var nowTime = new Date().Format("yyyy-MM-dd hh:mm:ss");
                var date1 = new Date();
                var data2 = new Date(date1);
                data2.setDate(date1.getDate() - 30);
                // 30天后时间
                var oneMonthTime = data2.Format("yyyy-MM-dd hh:mm:ss");
                for(var i=0;i<res.data.length;i++){
                    var skuListObj = {};
                    params.supplierId = res.data[i].shopId;
                    params.provinceId = provinceId;
                    params.cityId = cityId;
                    params.countyId = countyId;
                    params.address = cityList.join("");
                    skuListObj.skuNum =1;
                    skuListObj.shopId = res.data[i].shopId;
                    skuListObj.skuId = res.data[i].skuId;
                    skuListArr.push(skuListObj);
                    params.skuList = JSON.stringify(skuListArr);

                    var upTime = ''
                    if (res.data[i].upTime && res.data[i].upTime !== null) {
                        upTime = timeRender.toDateString(res.data[i].upTime);
                    }
                    if (upTime && caluDiffdays(upTime, oneMonthTime) <= 0 && caluDiffdays(nowTime, upTime) <= 30 ){
                        res.data[i].isNewGoods = 'yes';
                    }
                }
                searchSaleList(params, res.data, function(isBtn,arrList){
                    if(isBtn){
                        arr=arrList
                    }
                });
                setTimeout(function(){
                    domArr = arr;
                    var hotSaleAttrs = "";
                    var min = arr[0].sellPrice;
                    var index = 0;
                    for (var i = 0; i<arr.length; i++) {
                        if(arr[i].sellPrice < min){
                            min =arr[i].sellPrice;
                            index = i;
                        }
                        var pictureUrl = "<img ".concat(
                            arr[i].pictureUrl ? 'src="' + arr[i].pictureUrl + '"' : "", "></a>"
                        );
                        hotSaleAttrs += '<li class="compare-li">' ;
                        hotSaleAttrs += '<div class="compare-img">' ;
                        hotSaleAttrs += '<a href='+"detail?itemId="+arr[i].itemId+' target="_blank" class="picture">' ;
                        hotSaleAttrs += ''+ pictureUrl +'';
                        hotSaleAttrs += '<a class="compare-name" href='+"detail"+"?itemId="+arr[i].itemId+' target="_blank">'+arr[i].skuName+ '</a>' ;
                        if (KYCidList.length > 0 && KYCidList.indexOf(cid*1) !== -1) {
                            hotSaleAttrs += '<i class="kyLogo"></i>';
                        } else {
                            if (arr[i].isNewGoods === 'yes') {
                                hotSaleAttrs += '<i class="newGoodLogo"></i>';
                            }
                        }
                        hotSaleAttrs +='</div>' ;
                        hotSaleAttrs +='<div class="compare-font">' ;
                        hotSaleAttrs +='<a href=\"/mall-view/shop?shopId='+arr[i].shopId+'\"><span>'+arr[i].shopName+'</span></a>' ;
                        hotSaleAttrs +='<span style="margin-left: 30px;display: none;">有货</span>' ;
                        if ( KYCidList.length > 0 && KYCidList.indexOf(cid*1) !== -1) {
                            if (accountType == 2) {
                                hotSaleAttrs +='<p style="float: right;"><em>￥</em>'+ '<span class="sellPrice noPrice"></span>' +'</p>' ;
                            } else {
                                hotSaleAttrs +='<p style="float: right;"><em>￥</em>'+ '<span class="sellPrice">' + returnFloat(arr[i].sellPrice) + '</span>' +'</p>' ;
                            }
                        } else {
                            hotSaleAttrs +='<p style="float: right;"><em>￥</em>'+ '<span class="sellPrice">' + returnFloat(arr[i].sellPrice) + '</span>' +'</p>' ;
                        }
                        hotSaleAttrs +='</div>' ;
                        hotSaleAttrs +='<div class="noSale'+arr[i].saleState+'"></div>';
                        hotSaleAttrs +='</li>';
                    }
                    $('.compare-ul').append(hotSaleAttrs);
                    $('.compare-ul .noSale0').text('无货')
                    $(".compare-ul").niceScroll({
                        cursorcolor:"rgba(221,221,221,1)",
                        cursoropacitymax:1,
                        touchbehavior:false,
                        cursorwidth:"5px",
                        cursorborder:"0",
                        cursorborderradius:"5px"
                    });
                    if(pageNum == 1){
                        $(".compare-ul li").eq(index).find("a:first").append('<em>最低价</em>');
                    }
                    scrollFun ()
                },1000)
            }
        }, 'GET');
    }
    function initSelect(){
        var skuIdArr = JSON.stringify($("#attributes").val().split(";"));
        $(".color").find("li").each(function(i){
            //$(this).addClass("active");
            if($(this).parent().attr("index") == 0&&$(this).attr("class") == "active"){
                $("#selectOne").val($(this).attr("skuId"));
            }
            if($(this).parent().attr("index") == 1&&$(this).attr("class") == "active"){
                $("#selectTwo").val($(this).attr("skuId"));
            }
            if($(this).parent().attr("index") == 2&&$(this).attr("class") == "active"){
                $("#selectThree").val($(this).attr("skuId"));
            }
            if($(this).parent().attr("index") == 3&&$(this).attr("class") == "active"){
                $("#selectFour").val($(this).attr("skuId"));
            }
        })
    }
    function getItemSkuInfoListOne(parentIds,arrName){
        var shopId = $('#shopId').val();
        var params = {
            platformId:20,
            itemId:params_.itemId,
            parentIds: -1,
            selectArrName:arrName,
            shopId : shopId
        };

        //商品上下架的逻辑回滚，此逻辑不处理。
        //获取当前的sku
        var _skuid = getSkuId();
        getPrice(_skuid);
        getGoods();
    }
    function getItemSkuInfoListTwo(parentIds,arrName){
        var shopId = $('#shopId').val();
        var params = {
            platformId:20,
            itemId:params_.itemId,
            parentIds: parentIds,
            selectArrName:arrName,
            shopId : shopId
        };

        //商品上下架的逻辑回滚，此逻辑不处理。
        //获取当前的sku
        var _skuid = getSkuId();
        getPrice(_skuid);
        getGoods();
    }
    function getItemSkuInfoListTwoTwo(parentIds,arrName,clickId,arrNames){
        var shopId = $('#shopId').val();
        var params = {
            platformId:20,
            itemId:params_.itemId,
            parentIds: parentIds,
            selectArrName:arrName,
            shopId : shopId,
            index:1,
            clickId:clickId,
            arrNameClick:arrName,
            arrNameIds:parentIds,
            arrNames:arrNames,
            //arrNameArray:$("#arrNames").val().split(",")
        };

        //商品上下架的逻辑回滚，此逻辑不处理。
        //获取当前的sku
        var _skuid = getSkuId();
        getPrice(_skuid);
        getGoods();
    }
    function getItemSkuInfoListThree_one(parentIds,arrName){
        var shopId = $('#shopId').val();
        var params = {
            platformId:20,
            itemId:params_.itemId,
            parentIds: parentIds,
            selectArrName:arrName,
            shopId : shopId
        };
        //商品上下架的逻辑回滚，此逻辑不处理。
        //获取当前的sku
        var _skuid = getSkuId();
        getPrice(_skuid);
        getGoods();
    }
    function getItemSkuInfoListThree_two(parentIds,arrName){
        var shopId = $('#shopId').val();
        var params = {
            platformId:20,
            itemId:params_.itemId,
            parentIds: parentIds.join(","),
            selectArrName:arrName,
            shopId : shopId
        };

        //商品上下架的逻辑回滚，此逻辑不处理。
        //获取当前的sku
        var _skuid = getSkuId();
        getPrice(_skuid);
        getGoods();
    }
    function getItemSkuInfoListThree_three(arrName,clickId,arrNames,index){
        var shopId = $('#shopId').val();
        var params = {
            platformId:20,
            itemId:params_.itemId,
            parentIds: clickId,
            selectArrName:arrName,
            shopId : shopId,
            index:1,
            clickId:clickId,
            arrNameClick:arrName,
            arrNameIds:$("#selectOne").val(),
            arrNames:arrNames,
            //arrNameArray:$("#arrNames").val().split(",")
        };
        //商品上下架的逻辑回滚，此逻辑不处理。
        //获取当前的sku
        var _skuid = getSkuId();
        getPrice(_skuid);
        getGoods();
    }
    function getItemSkuInfoListThree_four(parentIds,arrName,clickId_,clickId,arrName_,arrNames,selectId,index){
        var shopId = $('#shopId').val();
        var params = {
            platformId:20,
            itemId:params_.itemId,
            parentIds: parentIds.join(","),
            selectArrName:arrName,
            shopId : shopId,
            index:2,
            clickId:clickId_,
            arrNameClick:arrName_,
            arrNameIds:parentIds.join(","),
            arrNames:arrNames.join(","),
            //arrNameArray:$("#arrNames").val().split(","),
            selectId:selectId
        };
        //商品上下架的逻辑回滚，此逻辑不处理。
        //获取当前的sku
        var _skuid = getSkuId();
        getPrice(_skuid);
        getGoods();
    }
    function getItemSkuInfoListFour_two(parentIds,arrName){
        var shopId = $('#shopId').val();
        var params = {
            platformId:20,
            itemId:params_.itemId,
            parentIds: parentIds.join(","),
            selectArrName:arrName,
            shopId : shopId
        };
        //商品上下架的逻辑回滚，此逻辑不处理。
        //获取当前的sku
        var _skuid = getSkuId();
        getPrice(_skuid);
        getGoods();
    }
    function getItemSkuInfoListFour_four(parentIds,arrName,clickId_,clickId,arrName_,arrNames,selectId,index){
        var shopId = $('#shopId').val();
        var params = {
            platformId:20,
            itemId:params_.itemId,
            parentIds: parentIds.join(","),
            selectArrName:arrName,
            shopId : shopId,
            index:index,
            clickId:clickId_,
            arrNameClick:arrName_,
            arrNameIds:parentIds.join(","),
            arrNames:arrNames.join(","),
            //arrNameArray:$("#arrNames").val().split(","),
            selectId:selectId
        };
        //商品上下架的逻辑回滚，此逻辑不处理。
        //获取当前的sku
        var _skuid = getSkuId();
        getPrice(_skuid);
        getGoods();
    }
    $('.compare-price').click(function(){
        $('.compare-detail')[0].style.display = 'inline';
        var skuId = $("#skuId").val();
        if( skuId&&skuPic&&skuPrice){
            var compareSku={itemId: params_.itemId,skuId:skuId,pictureUrl:skuPic,sellPrice:skuPrice,skuName:skuName, shopName: shopName,inventory:inventory}
            //config.pushArray("compareSkus", compareSku);
            var contrastObj = sessionStorage.getItem("contrastObj");
            !contrastObj ? contrastObj = {}: contrastObj = JSON.parse(contrastObj);
            var sku_key = contrastObj["a_"+skuId];
            var goods = objToArray(contrastObj);
            var length = goods.length;
            if(length == 4){
                //config.pushArray("compareSkus", compareSku);
                if(sku_key){
                    delete contrastObj["a_"+skuId];
                    sessionStorage.setItem("contrastObj",JSON.stringify(contrastObj));
                }else{
                    layer.msg("不能添加超过4个对比商品");
                }
            }else{
                if(sku_key){
                    delete contrastObj["a_"+skuId];
                    sessionStorage.setItem("contrastObj",JSON.stringify(contrastObj));
                }else{
                    contrastObj["a_"+skuId] = compareSku;
                    sessionStorage.setItem("contrastObj",JSON.stringify(contrastObj));
                }
            }
            goods = objToArray(contrastObj);
            var compareDetail = "";
            var firstSku,lastSku;
            for (var i = 0; i <goods.length; i++) {
                var goodsPictureUrl = "<img ".concat(
                    goods[i].pictureUrl ? 'src="' + goods[i].pictureUrl + '"' : "", ">"
                );

                compareDetail+='<li class="compare-li">' +
                    '<div class="compare-detail-div">' +
                    '<div class="compare-detail-pic">'+
                    // `<img ${goods[i].pictureUrl?'src="'+goods[i].pictureUrl+'"':''}>`+
                    ''+ goodsPictureUrl +''+
                    /* '<span class="inventory">'+config.getKey("compareSkus")[i].inventory+'件</span>'+*/
                    '</div>'+
                    '<div class="compare-detail-text">'+
                    '<p>'+goods[i].skuName+'</p>'+
                    '<span class="skuName">'+goods[i].shopName+'</span>'+
                    '<span class="skuPrice">'+"￥"+returnFloat(goods[i].sellPrice)+'</span>'+
                    '</div>'+
                    '</div>'+
                    '</li>';
                if(goods.length>=2){
                    if(i==0){
                        firstSku = goods[i].skuId;
                    }
                    if(i==goods.length-1){
                        lastSku = goods[i].skuId;
                    }
                }
            }
            for(var j = goods.length;j<4;j++){
                compareDetail+='<li class="compare-li">' +
                    '<div class="noSkusNum">'+ parseInt(j+1) +'</div>'+ '<span class="noSkusName">请添加您所需要的商品</span>'+
                    '</li>';
            }
            $('.compare-detail-ul').html(compareDetail);
            if(goods.length <= 4){
                if(firstSku && lastSku){
                    admin.req(config.item_server+ '/mall/item/compare/addSkuCompareRecord', {platformId:20,skuId:firstSku,compaerSkuid:lastSku}, function (res) {
                        if (res.code == 0) {
                            //layer.msg("加入对比成功");
                        }
                    },"POST");
                }
            }

        }else{
            layer.msg("请选择商品属性");
        }


    })
    function objToArray(obj) {
        var array =[];
        if(obj){
            for(var key in obj){
                if(key && obj[key]){
                    array.push(obj[key]);
                }
            }
        }
        return array;
    }

    $('#compare-hidden').click(function(){
        $('.compare-detail')[0].style.display = 'none';

    })
    $('#compare-clean').click(function(){
        sessionStorage.removeItem("contrastObj");
        var compareDetail = '';
        for(var j = 0;j<4;j++){
            compareDetail+='<li class="compare-li">' +
                '<div class="noSkusNum">'+ parseInt(j+1) +'</div>'+ '<span class="noSkusName">请添加您所需要的商品</span>'+
                '</li>';
        }
        $('.compare-detail-ul').html(compareDetail);
    })

    /*function querySkuInfo(parentIds,arrName){
        var shopId = $("#shopId").val();
        //TODO 多个属性如何交互
        if(parentIds&&arrName){
            admin.req(config.item_server+ '/mall/search/queryItemSkuInventory', {platformId:20,shopId:shopId,parentIds: parentIds, selectArrName: arrName, itemId: params_.itemId}, function (res) {
                var skuIds=[];
                if (res.code == 0) {
                    skuId = res.data.itemSkuInventoryVoList[0].id;
                    inventory = res.data.itemSkuInventoryVoList[0].inventory;

                    skuIds.push(skuId)
                    $("#skuId").val(skuIds);
                    admin.req(config.item_server+ '/mall/search/querySkuPrice', {platformId:20, skuIds:JSON.stringify(skuId)}, function (res) {
                        setSpecificationData(specificationData.skuSpecMap[skuId]);
                        if (res.code == 0) {
                            successData = res.data[0];
                            if(res.data == null || res.data[0].sellPrice < 0){
                                $(".price b").html("暂无报价");
                                $(".price em").hide();
                                $(".price i").hide();
                                $(".price del").hide();
                            }else{
                                $(".price b").html(returnFloat(res.data[0].sellPrice));
                                $(".price del").html(returnFloat(res.data[0].marketPrice));
                                $(".price em").show();
                                $(".price i").show();
                                $(".price del").show();
                            }
                            skuPic = res.data[0].pictureUrl[0].pictureUrl;
                            skuPrice = res.data[0].sellPrice;
                            skuName= res.data[0].skuName;
                            shopName= res.data[0].shopName;
                            var score = res.data[0].score;
                            if(Math.floor(score) == score){
                                //评分设置
                                rate.render({
                                    elem: '#score'
                                    ,value: Math.floor(score)
                                    ,half: true
                                    ,readonly: true
                                    ,theme: '#FFB800'
                                });
                            }else{
                                //评分设置
                                rate.render({
                                    elem: '#score'
                                    ,value: score
                                    ,half: true
                                    ,readonly: true
                                    ,theme: '#FFB800'
                                });
                            }

                            //销量设置
                            $("#saleNum").html(res.data[0].saleNum?res.data[0].saleNum:0);
                            if(res.data[0].packinglist.length >= 0){
                                $('#packingList').html("");
                                res.data[0].packinglist.forEach(function(ele,index){
                                    var html ='';
                                    html =' <div><p>'+ele.annexName +'</p><p>X1</p></div>';
                                    $('#packingList').append(html);
                                });
                            }
                            $('.smallImg').html("");
                            $("#bigImg").attr('src',res.data[0].pictureUrl[0].pictureUrl);
                            var smallImg = "";
                            for (var i = 0; i < res.data[0].pictureUrl.length; i++) {
                                if (i == 0) {
                                    smallImg += '<li class="active"><img  src=' + res.data[0].pictureUrl[i].pictureUrl + '></li>'
                                } else {
                                    smallImg += '<li><img  src=' + res.data[0].pictureUrl[i].pictureUrl + '></li>'
                                }
                            }
                            $('.smallImg').append(smallImg);
                            //$("#fullCategory").append('<span> '+ res.data[0].skuName +'</span>');
                            $("#itemName").text(res.data[0].skuName);
                            //$("#packingList").text(res.data[0].packinglist);
                            $('.smallImgUp').addClass('disabled');
                            $('.smallImgDown').removeClass('disabled');
                            var curIndex = 0;
                            imgScroll(curIndex);
                        }
                    }, 'GET');
                }
            }, 'GET');
        }
    }*/
    // 预警提示 供应商黑名单提示弹窗
    if (accountType === 3) {
        Tips();
    }
    function  Tips() {
        // 调用接口 判断弹窗显示隐藏
        admin.req(config.user_server+ '/seller/user/checkBlackType', {platformId:20}, function (res) {
            if (res.code == '0' && res.data) {
                var earlyData = res.data;
                var dt = earlyData.stime;
                var stime = dt.replace(/-/g,"/");
                var dtt = earlyData.etime;
                var etime = dtt.replace(/-/g,"/");
                var time = stime+'-'+etime;
                layer.open({
                    type: 1,
                    title: '提示',
                    skin: 'layui-layer-earlyWarning', //样式类名
                    area: ['545px', '340px'], //宽高
                    btn:['关闭'],
                    content: '<div class="msgContent">'+
                        '<p>'+ earlyData.content +'</p>'+
                        '<p>'+ '<span>处罚周期：</span>' + time +'</p>'+
                        '<p>'+ '<span>详情请致电咨询：</span>' +earlyData.phone +'</p>'+
                        '<p style="" class="earlyWarning-checkbox">'+ '<input type="checkbox" name="earlyWarning" value="1" title="" lay-skin="primary" style="width: 18px;height: 29px;">'+ '</p>'+
                        '<span style="" class="earlyWarning_dontRemind">'+ "不再提醒" +'</span>'+
                        '</div>'
                    ,yes: function(index, layero){
                        //按钮【按钮一】的回调
                        if ($("input:checkbox[name='earlyWarning']:checked").length == 0) {
                            layer.close(layer.index);
                        }else {
                            var del;
                            admin.req(config.user_server+ '/seller/user/checkBlackType', {del: 1,platformId:20}, function (res) {
                                layer.close(layer.index);
                            }, 'GET');
                        }
                    }
                });
            }
        }, 'GET');
    }
    //商品价格处理
    function priceRender(res){
        if(res.data == null || res.data.length == 0 || successData.sellPrice < 0|| !successData.sellPrice){
            $(".price b").html("暂无报价");
            $(".price i").hide();
            $(".price em").hide();
            $(".price del").hide();
        } else {
            if(specificationData.saleStatus==20){
                $(".price b").html("此为无效商品！");
            }else {
                $(".price b").html(returnFloat(successData.sellPrice));
            }
            $(".price del").html(returnFloat(successData.marketPrice));
            $(".price i").show();
            $(".price em").show();
            $(".price del").show();
        }
    }

    function getPrice(skuId){
        var cid = $("#cid").val();
        $(".compare-ul").html("");
        getCommodite(skuId,cid,pageNum);
        $("#skuId").val(skuId);

        admin.req(config.item_server+ '/mall/search/querySkuPrice', {platformId:20, skuIds:JSON.stringify(skuId)}, function (res) {
            // setSpecificationData(specificationData.skuSpecMap[skuId]);
            setSpecificationData(skuArrInfo);
            if (res.code == 0) {
                successData = res.data[0];

                getCategory(function callback() {
                    if (KYCidList.indexOf(cid*1) !== -1) {
                        if(accountType == 2) {
                            $(".price b").html("").addClass('noPrice');
                            $(".price del").html(returnFloat(successData.marketPrice));
                            $(".price i").show();
                            $(".price em").show();
                            $(".price del").show();
                        } else {
                            priceRender(res);
                        }
                        if ($(".img-big .kyLogo").length < 1) {
                            $(".img-big").append('<i class="kyLogo"></i>');
                        }
                    } else {
                        priceRender(res);
                    }
                });

                // 根据skuInfoList中isNewGoods 字段判断是否为30内新商品
                if ($(".img-big").has('.newGoodLogo').length > 0) {
                    $(".img-big .newGoodLogo").remove();
                }
                skuInfoList.forEach(function(item, index){
                    if (skuId === item.id) {
                        if ($(".img-big").has('.kyLogo').length == 0) { // 说明不是防疫物资，可以显示新品图标
                            if (item.isNewGoods === 'yes') {
                                $(".img-big").append('<i class="newGoodLogo"></i>');
                            } else {
                                $(".img-big .newGoodLogo").remove();
                            }
                        }
                    }
                });

                //生产环境临时版本，草稿状态不获取值
                if(res.data == null || res.data.length == 0 || res.data[0].sellPrice < 0|| !res.data[0].sellPrice){
                    return false;
                }

                skuPic = res.data[0].pictureUrl[0].pictureUrl;
                skuPrice = res.data[0].sellPrice;
                skuName= res.data[0].skuName;
                shopName= res.data[0].shopName;
                var score = res.data[0].score;
                if(Math.floor(score) == score){
                    //评分设置
                    rate.render({
                        elem: '#score'
                        ,value: Math.floor(score)
                        ,half: true
                        ,readonly: true
                        ,theme: '#FFB800'
                    });
                }else{
                    //评分设置
                    rate.render({
                        elem: '#score'
                        ,value: score
                        ,half: true
                        ,readonly: true
                        ,theme: '#FFB800'
                    });
                }
                //销量设置
                $("#saleNum").html(res.data[0].saleNum?res.data[0].saleNum:0);
                // 包装清单设置  start
                if(res.data[0].packinglist.length >= 0){
                    $('#packingList').html("");
                    res.data[0].packinglist.forEach(function(ele,index){
                        var html ='';
                        if(res.data[0].shopType ==2){
                            // console.log("ele.annexName",ele.annexName);
                            if (ele.annexName && ele.annexName.indexOf('`') != -1) {
                                var annexNames = ele.annexName.split("``");
                                annexNames.forEach(function(ele,index){
                                    if (ele.split("*")[0] && ele.split("*")[1]) {
                                        html =' <div><p>'+ele.split("*")[0] +'</p><p>'+ele.split("*")[1]+'</p></div>';
                                        $('#packingList').append(html);
                                    }
                                })
                            } else {
                                if (ele.annexName && ele.annexName.split("*")[0] && ele.annexName.split("*")[1]) {
                                    html = ' <div><p>' + ele.annexName.split("*")[0] + '</p><p>' + ele.annexName.split("*")[1] + '</p></div>';
                                    $('#packingList').append(html);
                                }
                            }
                        }else {
                            html =' <div><p>'+ele.annexName +'</p><p>X1</p></div>';
                            $('#packingList').append(html);
                        }
                    });
                }
                 // 包装清单设置  end
                $('.smallImg').html("");
                $('.smallImg').css("margin-left","0px");
                $("#bigImg").attr('src',res.data[0].pictureUrl[0].pictureUrl);
                $("#bigImg-big").attr('src',res.data[0].pictureUrl[0].pictureUrl);
                initImgPositionBox()
                var smallImg = "";
                skuImgNum=res.data[0].pictureUrl.length;
                var pictureUrlArr = res.data[0].pictureUrl;
                for (var i = 0; i < pictureUrlArr.length; i++) {
                    if (i == 0) {
                        smallImg += '<li class="active"><img  src=' + pictureUrlArr[i].pictureUrl + ' alt="图片'+ i +'"></li>'
                    } else {
                        if (pictureUrlArr[i].pictureUrl !== pictureUrlArr[i-1].pictureUrl){
                            smallImg += '<li><img  src=' + pictureUrlArr[i].pictureUrl + ' alt="图片'+ i +'"></li>'
                        }
                    }
                }
                $('.smallImg').append(smallImg);
                //$("#fullCategory").append('<span> '+ res.data[0].skuName +'</span>');
                $("#itemName").text(res.data[0].skuName);
                $('.smallImgUp').addClass('disabled');
                $('.smallImgDown').removeClass('disabled');
                var curIndex = 0;
                imgScroll(curIndex);
                // 商品信息
                var parameterHtml = ''
                var skuInfo = skuInfoList;
                if (skuInfo && skuInfo.length > 0) {
                    for (var s = 0; s < skuInfo.length; s++) {
                        var data = skuInfo[s];
                        var modelCode = data.modelCode == null || data.modelCode == '' ? '暂无' : data.modelCode; //规格型号
                        var barCode = data.barCode == null || data.barCode == '' ? '暂无' : data.barCode; //商品条码
                        var weight = ''; //商品毛重
                        if (data.weight && data.weightUnit) {
                            weight = data.weight + data.weightUnit
                        } else {
                            weight = '暂无';
                        }
                        var packageDis = data.packageDis == null || data.packageDis == '**' ? '暂无' : data.packageDis+'毫米'; //包装尺寸
                        var skuUnit = data.skuUnit == null || data.skuUnit == '' ? '暂无' : data.skuUnit; //商品单位
                        if (data.id == res.data[0].skuId) {
                            parameterHtml +='<li title="'+ data.id +'">单品编码：'+ data.id +'</li>';
                            parameterHtml +='<li title="'+ modelCode +'">规格型号：'+ modelCode +'</li>';
                            parameterHtml +='<li title="'+ barCode +'">商品条码：'+ barCode +'</li>';
                            parameterHtml +='<li title="'+ weight +'">商品毛重：'+ weight +'</li>';
                            parameterHtml +='<li title="'+ packageDis +'">包装尺寸：'+ packageDis +'</li>';
                            parameterHtml +='<li title="'+ skuUnit +'">商品单位：'+ skuUnit +'</li>';
                            break;
                        }
                    }
                }

                $('.des-parameter #dynamic-parameter').html(parameterHtml)
            }else{
                rate.render({
                    elem: '#score'
                    ,value: 0
                    ,half: true
                    ,readonly: true
                    ,theme: '#C9C9C9'
                });
            }
        }, 'GET');
    }
    function getArrDifference(arr1, arr2) {
        var newArr = [];
        for (var i = 0; i < arr2.length; i++) {
            for (var j = 0; j < arr1.length; j++) {
                if(arr1[j] === arr2[i]){
                    newArr.push(arr1[j]);
                }
            }
        }
        return newArr;
    }

    function skuArrList(arr){
        for(var i=0;i<arr.length;i++){
            for(var j=i+1;j<arr.length;j++){
                if(arr[i].split(':')[0]==arr[j].split(':')[0]){
                    arr.splice(j,1);
                    skuArrList(arr)
                }
            }
        }
    }

    //底部销售属性
    function setSpecificationData(data){
        var specActives=$('.color').find('.active');//获取页面展示的可选中的销售属性
        var specArr2=[],obj=data[0],specAttributesArr=[];
        for(var i=0;i<specActives.length;i++){
            specArr2.push(specActives.eq(i).attr('skuId'))//拿到页面展示的可选择的销售属性的skuid
        }
        for(var i=0;i<data.length;i++){
            var dataArr1=data[i].attributes.split(',');
            var getSpecArr3=getArrDifference(dataArr1, specArr2);//找出可选销售属性的skuid与data中attributes的相同项
            if(getSpecArr3.length==dataArr1.length){
                obj=data[i];//得到该skuid下的销售属性对象
            }
        }
        var htmlStr='';
        if(obj.attributesName){//页面可选销售属性
            var arr3=obj.attributesName.split(';');
            for(var i=0;i<arr3.length;i++){
                if(arr3[i]!='null'&&arr3[i]!=''){
                    var specification1 = arr3[i].slice(0,arr3[i].indexOf(':'));
                    var specification2 = arr3[i].slice(arr3[i].indexOf(':')+1);
                    htmlStr+='<div class="specification"><span class="specification-attr2">'+ specification1 +'</span><span class="specification-attr" >'+ specification2 +'</span></div>'
                }
            }
        }
        if(obj.specAttributes){//不可选销售属性
            specAttributesArr=obj.specAttributes.split(';');
            for(var i=0;i<specAttributesArr.length;i++){
                var skuStr=specAttributesArr[i].slice(specAttributesArr[i].indexOf(':')+1);
                for(var j=i+1;j<specAttributesArr.length;j++){
                    if(specAttributesArr[i].split(':')[0]==specAttributesArr[j].split(':')[0]){
                        skuStr+='，'+specAttributesArr[j].split(':')[1];
                    }
                }
                //相同销售属性，属性值，隔开
                if(specAttributesArr[i]!='null'&&specAttributesArr[i]!=''){
                    if(specAttributesArr[i].split(':')[0]){
                        specAttributesArr[i]=specAttributesArr[i].split(':')[0]+':'+skuStr;
                    }
                }
            }
            skuArrList(specAttributesArr); //去重
            for(var i=0;i<specAttributesArr.length;i++){
                if(specAttributesArr[i]!='null'&&specAttributesArr[i]!=''){
                    var specification3 = specAttributesArr[i].slice(0,specAttributesArr[i].indexOf(':'));
                    var specification4 = specAttributesArr[i].slice(specAttributesArr[i].indexOf(':')+1);
                    htmlStr+='<div class="specification"><span class="specification-attr2">'+specification3+'</span><span class="specification-attr" >'+specification4+'</span></div>'
                }
            }
        }
        if(obj.materialCode){//物资编码
            $('.material-code').show();
            $('#code').text(obj.materialCode)
        }else{
            $('.material-code').hide();
        }
        $("#weight").html(htmlStr)
    }


    function imgScroll(curIndex){
        var interval = $("#imageMenu li:first").width();
        var count = $("#imageMenu li").length - 4;

        $('.scrollbutton').unbind().click(function(){
            if($("#imageMenu li").length <4) $('.smallImgDown').addClass('disabled');
            if( $(this).hasClass('disabled') ) return false;

            if ($(this).hasClass('smallImgUp')){
                curIndex --;
            } else {
                curIndex++;
                if(curIndex>=skuImgNum){
                    $(".smallImgDown").addClass('disabled');
                    return false;
                }else{
                    $(".smallImgDown").removeClass('disabled');
                }
            }

            $('.scrollbutton').removeClass('disabled');
            if (curIndex == 0) $('.smallImgUp').addClass('disabled');
            if(curIndex == count) $('.smallImgDown').addClass('disabled');
            if($(this).hasClass('smallImgUp') && curIndex==9){
                curIndex=8;
            }
            if($(this).hasClass('smallImgUp')){
                $("#imageMenu ul").stop(false, true).animate({"marginLeft" : -curIndex*interval-15*curIndex + "px"}, 600);
            }else{
                $("#imageMenu ul").animate({"marginLeft" : -curIndex*interval-15*curIndex + "px"}, 600);
            }
            $("#imageMenu li").removeClass("active");
            $("#imageMenu li").eq(curIndex).attr("class","active");
            var imgUrl = $("#imageMenu li").eq(curIndex).find("img").attr("src");
            $("#bigImg").attr("src",imgUrl);
            $("#bigImg-big").attr("src",imgUrl);
            initImgPositionBox()
        });
        $(".smallImg li").unbind().click(function(){
            if($(this).attr("class") != 'active'){
                var imgUrl = $(this).find("img").attr("src");
                $("#bigImg").attr("src",imgUrl);
                $("#bigImg-big").attr("src",imgUrl);
                initImgPositionBox()
                $(this).attr("class","active");
                $(this).siblings().removeClass('active');
                curIndex = $(this).index();
            }
        })
    }

    //历史价格
    $('.history').click(function(){
        var skuId = $("#skuId").val();
        layer.open({
            type: 2, //类型，解析url
            skin: 'demo-pic',
            closeBtn: 1, //关闭按钮是否显示 1显示0不显示
            title: "历史价格", //页面标题
            shadeClose: true, //点击遮罩区域是否关闭页面
            shade: 0.8,  //遮罩透明度
            area: ['85%', '85%'],  //弹出层页面比例
            content: 'priceTrend?skuId='+skuId,  //弹出层的url
            btn: ['确定','取消']
        });

    });
    function scrollFun (){
        $(".compare-ul").unbind("scroll").bind("scroll", function (e) {
            var skuId = $("#skuId").val();
            var cid = $("#cid").val();
            var sum = this.scrollHeight;
            if (sum <= Math.ceil($(this).scrollTop()) + $(this).height()&& $(this).scrollTop()> 0) {
                if(domArr.length%3==0&&domArr.length>0){
                    pageNum += 1;
                    getCommodite(skuId,cid,pageNum);
                }
            }
        });
    }


    $('.openCustomerService').click(function(){
        var skuId = $("#skuId").val();
        admin.req(config.item_server + '/mall/item/customer/checkCustomer', {skuId:skuId}, function (res) {
            if (res.code == 0) {
                window.open(res.data,"_blank");
            } else {
                layer.msg(res.msg)
            }
        }, 'POST');


    });
    //收藏
    $('.collec').click(function(){
        if($('.collec span').text() == ' 收藏'){
            saveCollection();
        }else{
            cancelCollection();
        }


    });
    function cancelCollection(skuId){
        var params ={skuIds:$("#skuId").val()};
        admin.req('/item/mall/collection/cancelCollectionBySkuIds', params, function (result) {
            if(result.code=='0'){
                $("#fa-iconPosition").removeClass("fa-heart");
                $("#fa-iconPosition").addClass("fa-heart-o");
                // $('.fa-heart')[0].style.color = "#3399ff";
                $('.collec span')[0].innerText= ' 收藏';
                $('.collec span')[0].style.color='';
            }else{
                layer.msg(result.msg);
            }
        }, 'get');
    }
    function saveCollection(){
        var params ={skuIds:$("#skuId").val(),collectionSource:30};
        admin.req('/item/mall/collection/saveCollectionItems', params, function (result) {
            if(result.code=='0'){
                $("#fa-iconPosition").removeClass("fa-heart-o");
                $("#fa-iconPosition").addClass("fa-heart");
                // $('.fa-heart-o')[0].style.color = "#3399ff";
                $('.collec span')[0].innerText='已收藏';
                $('.collec span')[0].style.color='#3399ff';
            }else{
                layer.msg(result.msg);
            }
        }, 'get');
    }
    function insertVisitRecord() {
        var host = config.host;
        var cookieFinger = getCookie("cookieFinger");
        if (null == cookieFinger) {
            //如果cookie为空，说明是刚来的，得创建一个cookieFinger
            cookieFinger = createCookieFinger(config.version, config.random_num);
            setCookie(host, cookieFinger, 1);
        }
        var visitorType = 1;
        var accountId = undefined;
        var platformId = undefined;
        if (config.getUser() == undefined) {
            visitorType = 0;
        } else{
            accountId = config.getUser().accountId;
        }
        var itemShopId = $("#shopId").val();
        var skuId = $("#skuId").val();
        // var param = {accountId, platformId,itemShopId, skuId,visitorType, cookieFinger};
        var param = {
            accountId: accountId,
            platformId: platformId,
            itemShopId: itemShopId,
            skuId: skuId,
            visitorType: visitorType,
            cookieFinger: cookieFinger
        };
        admin.req(config.static_server + "/mall/flow/statistic/insertVisitRecord", param, function(data){}, "POST");
    }
    function getCollection(){
        var params ={skuIds:$("#skuId").val()};
        admin.req('/item-service/mall/collection/queryUserCollect', params, function (result) {
            if(result.code=='0'){
                if(result.data[$("#skuId").val()]){
                    $("#fa-iconPosition").removeClass("fa-heart-o");
                    $("#fa-iconPosition").addClass("fa-heart");
                    // $('.fa-heart-o')[0].style.color = "#3399ff";
                    $('.collec span')[0].innerText='已收藏';
                    $('.collec span')[0].style.color='#3399ff';
                }
            }else{
                //layer.msg(result.msg);
            }
        }, 'get');
    }
    function setBrowseRecord(){
        var params ={skuId:$("#skuId").val(),categoryId:$("#cid").val()};
        admin.req('/item-service/mall/item/addSkuVisitTrack', params, function (result) {
            if(result.code=='0'){

            }else{
                //layer.msg(result.msg);
            }
        }, 'get');
    }

    function getShopFreightAmount(shopId){
        admin.req(config.platform_server+ '/mall/delivery/queryFreightAmountShopId', {shopId: shopId}, function (res) {
            if (res.code == 0) {
                $('.packageAmount>span').html(res.data != null && res.data.freightPrice ? res.data.freightPrice.toFixed(2) : 0.00)
            } else {
                $('.packageAmount').hide();
            }
        }, 'GET');
    }
    // 加入购物车
    $(document).on('click', '.addToCart', function(e){
        e.stopPropagation();
        if(accountType === 2){
            addCart();
        }
    })

    function addCart() {
        if(noAdd){
            return false;
        }
        var saleNum = $("#valShow").val();
        var skuId = $("#skuId").val();
        if (saleNum <= 0) {
            layer.msg("请选择购买数量");
            return false;
        }
        if (!skuId) {
            layer.msg("加入购物车失败，请重新操作");
            return false;
        }

        var skuIdAndSaleNum = [];
        var skuIdAndSaleArray = {saleNum: saleNum, skuId: skuId};
        skuIdAndSaleNum.push(skuIdAndSaleArray);
        admin.req(config.order_server + '/mall/cart/addCart', {skuSaleNumJson:JSON.stringify(skuIdAndSaleNum)}, function (res) {
            if (res.code == 0) {
                layer.open({
                    type: 1
                    ,title: "添加成功" //不显示标题栏
                    ,area: ['480px','400px']
                    ,shade: 0.8
                    ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
                    ,resize: false
                    ,btn: ['前往购物车', '继续购物']
                    ,moveType: 1 //拖拽模式，0或者1
                    ,content: '<div class="box"><div class="box1"></div><div class="box2"></div></div>' +
                        '<div class="box-name">商品已成功加入购物车</div>'+
                        '<div class="box-goods"><img src="'+ successData.pictureUrl[0].pictureUrl+'" style="width: 90px; height: 90px;"><span class="first-span">'+ successData.skuName+'</span><span class="second-span">数量：'+ saleNum +'</span></div>'
                    ,success: function(layero){
                        getCartItemNum();
                        var btn = layero.find('.layui-layer-btn');
                        btn.find('.layui-layer-btn0').attr({
                            href: '/mall-view/cart'
                        });
                    }
                });
            } else {
                layer.msg(res.msg,{icon: 0})
            }
        }, 'POST');
    }
    function getCartItemNum(){
        admin.req(config.order_server+'/mall/cart/queryCartItemNum',{},function (res) {
            if(res.code == 0){
                $('.cart-mini-num').text((res.data>99?'99+':res.data || 0));
            }
        },'GET');
    }
});

var fixedDom = $("#road-tab"),
    tabclass = "road-tab-fixed";
var beforeElementScrollTop = 0;
var beforeOffsetTop = fixedDom[0].offsetTop;
//scroll节流

var throttle = function throttle(func, wait, mustRun) {
    var timeout,
        startTime = new Date();
    return function() {
        var context = this,
            args = arguments,
            curTime = new Date();
        clearTimeout(timeout); // 如果达到了规定的触发时间间隔，触发 handler

        if (curTime - startTime >= mustRun) {
            beforeElementScrollTop = document.documentElement.scrollTop;
            func.apply(context, args);
            startTime = curTime; // 没达到触发间隔，重新设定定时器
        } else {
            timeout = setTimeout(func, wait);
        }
    };
};

var winScroll = function winScroll(e) {
    var elementScrollTop = document.documentElement.scrollTop;
    if (beforeElementScrollTop - elementScrollTop <= 0) {
        //up
        if (beforeOffsetTop - elementScrollTop < 0) {
            fixedDom.addClass("road-tab-fixed");

            if (accountType != 2) {
                $(".addcar2").show();
            } else {
                $(".addcar").show();
            }
        }
    } else {
        if (beforeOffsetTop - elementScrollTop >= 0) {
            fixedDom.removeClass("road-tab-fixed");
            $(".addcar").hide();
        }
    }
};

$(window).off("scroll").on("scroll", throttle(winScroll, 10, 100));



