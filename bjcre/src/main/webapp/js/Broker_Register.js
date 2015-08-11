;

APF.Namespace.register("anjuke.Broker.Navigation");

anjuke.Broker.Navigation = {
    initialize: function() {
        this._subNavMask();
        this._login_panel();
    },

    _subNavMask: function() {
        $('.nav-item-iframe').each(function(){
            $(this).height($(this).next('.nav-item-list').height());
            $(this).width($(this).next('.nav-item-list').outerWidth());
        });
    },

    _login_panel: function() {
        jQuery.ajax({
            url : "/ajkbroker/ajax/checklogin/?r=" + Math.random(),
            type : "get",
            dataType : "json",
            success : function (response) {
                if (response.user_info.user_id != 0) {
                    //经纪人姓名
                    $('#js_truename').html('您好，'+response.user_info.true_name);

                    //未读消息
                    var strSms = '';
                    if(response.msg_stats.totalUnreadCount > 0) {
                        strSms = '<a href="'+response.nav_urls.letter+'">消息<em>'+response.msg_stats.totalUnreadCount+'</em></a>';
                    }else{
                        strSms = '<span class="not-mes"><a style="background-color: #999999;border: none;" href="' + response.nav_urls.letter + '">消息<em>0</em></a></span>';
                    }
                    $('#js_sms').html(strSms);

                    //经纪人社区
                    $('#js_bbs').html('<a href="'+response.nav_urls.bbs+'">经纪人社区</a>');

                    $('#ajk_shop_edit').attr('href', response.nav_urls.editshop.ajk);
                    $('#zf_shop_edit').attr('href', response.nav_urls.editshop.zf);
                    if ($('#jp_shop_edit')){
                        $('#jp_shop_edit').attr('href', response.nav_urls.editshop.jp);
                    }

                    $('#nav_link_help').attr('href', response.nav_urls.help);

                    $('#nav_link_logout').attr('href', response.nav_urls.logout);
                }
            }
        });
    }
};var pregflag_mobile = false;
var pregflag_pwd = false;
var pregflag_chepwd = false;
var pregflag_truename = false;

function create_area_info(cityid, info) {
    if (cityid < 11) {
        jQuery("#citytip").children().hide();
        jQuery("#citytip .tip_area_2").show();
    } else {
        jQuery("#citytip").children().hide();
        jQuery.getJSON("/ajkbroker/broker/areainfo/", {"cityid":cityid, "act":"area"}, function (json) {
            var str = "";
            jQuery.each(json, function (k, v) {
                str += '<li><a typeid="' + v.typeId + '" href="javascript:void(0);">' + v.typeName + '</a></li>';
            });
            jQuery("#searchlistarea").html(str);
        });
        if (!info || !info.areaid) {
            jQuery("#selectareatext").html("请选择区域");
	jQuery("#selectareaid").val(0);
        }
        if (!info || !info.blockid) {
            jQuery("#selectblocktext").html("请选择板块");
	jQuery("#selectblockid").val(0);
        }
        jQuery("#searchlistblock").hide();
        jQuery("#searchlistarea").hide();
        if (!info || !info.companyid) {
            jQuery("#companytext").val("");
            jQuery("#companyid").val(0);
        }
        jQuery("#store").hide();
        if (!info || !info.storeid) {
            jQuery("#storename").val("");
            jQuery("#storeid").val(0);
        }

    }
}
function create_block_info(typeid) {
    if (typeid > 0) {
	jQuery("#areatip").children().hide();
        jQuery.getJSON("/ajkbroker/broker/areainfo/", {"typeid":typeid, "act":"block"}, function (json) {
            var str = "";
            jQuery.each(json, function (k, v) {
                str += '<li><a typeid="' + v.typeId + '" href="javascript:void(0);">' + v.typeName + '</a></li>';
		});
            jQuery("#searchlistblock").html(str);
	});
    } else {
        jQuery("#areatip").children().hide();
        jQuery("#areatip .tip_area_3").show();
    }
}

function check_mobile() {
		var reg = /^(1|2)\d{10}/;
    var t = jQuery("#mobile");
    if (t.val() == "") {
			jQuery("#mobiletip").children().hide();
			jQuery("#mobiletip .tip_mobile_3").show();
			pregflag_mobile = false;
    } else if (!reg.test(t.val())) {
			jQuery("#mobiletip").children().hide();
			jQuery("#mobiletip .tip_mobile_5").show();
			pregflag_mobile = false;
    } else {
        jQuery.getJSON("/ajkbroker/broker/register/", { "mobile":t.val()}, function (json) {
            if (undefined != json && json == "pass") {
					jQuery("#mobiletip").children().hide();
					jQuery("#mobiletip .tip_mobile_4").show();
					pregflag_mobile = false;
            } else if (undefined != json && json == "nopass") {
					jQuery("#mobiletip").children().hide();
					jQuery("#mobiletip .tip_mobile_4").show();
					pregflag_mobile = false;
            } else if(undefined != json && json == "userUsed"){
                    jQuery("#mobiletip").children().hide();
                    jQuery("#mobiletip .tip_mobile_7").show();
                    pregflag_mobile = false;
            } else {
					jQuery("#mobiletip").children().hide();
					jQuery("#mobiletip .tip_mobile_2").show();
					pregflag_mobile = true;
				}
			});
		}
    return pregflag_mobile;
}

function check_password() {
    var reg = /\s+/g;
    var t = jQuery("#password");
    if (t.val() == "") {
			jQuery("#passwordtip").children().hide();
			jQuery("#passwordtip .tip_password_3").show();
        pregflag_pwd = false;
    } else if (reg.test(t.val())) {
			jQuery("#passwordtip").children().hide();
			jQuery("#passwordtip .tip_password_5").show();
        pregflag_pwd = false;
    } else if (t.val().length < 6 || t.val().length > 16) {
			jQuery("#passwordtip").children().hide();
			jQuery("#passwordtip .tip_password_4").show();
        pregflag_pwd = false;
    } else {
			jQuery("#passwordtip").children().hide();
			jQuery("#passwordtip .tip_password_2").show();
        pregflag_pwd = true;
		}
    return pregflag_pwd;
}

function check_checkpwd() {
    var t = jQuery("#checkpwd");
    if (t.val() == "") {
        jQuery("#pwdtip").children().hide();
        jQuery("#pwdtip .tip_pwd_2").show();
        pregflag_chepwd = false;
    } else if (t.val() != jQuery("#password").val()) {
			jQuery("#pwdtip").children().hide();
			jQuery("#pwdtip .tip_pwd_3").show();
        pregflag_chepwd = false;
    } else {
			jQuery("#pwdtip").children().hide();
			jQuery("#pwdtip .tip_pwd_1").show();
        pregflag_chepwd = true;
		}
    return pregflag_chepwd;
}

function check_truename(data) {
    var reg = /\s+/g;
    var t = jQuery("#truename");
    if (t.val() == "" || reg.test(t.val())) {
        jQuery("#nametip").children().hide();
        jQuery("#nametip .tip_name_3").html("请填写您的真实姓名(请勿使用空格)。");
        jQuery("#nametip .tip_name_3").show();
        pregflag_truename = false;
    }else {
        jQuery("#nametip").children().hide();
			jQuery("#nametip .tip_name_2").show();
			pregflag_truename = true;
		}
    return pregflag_truename;
}

//检查主营业务是否为空
function check_mainbusiness(){
    mainBusiness = jQuery("input[name='mainbusiness']");
    var uncheckflag = 0;
    jQuery.each(mainBusiness, function(i,val){
        if(val.checked === true){
            uncheckflag++;
        }
    });
    if(uncheckflag > 0){
        return true;
    }else{
        return false;
    }
}

//检查城市是否可以手动选择主营业务
function check_mainbusiness_access(){
    var city_id = jQuery('#selectcityid').val();
    var available_citylist = jQuery('#main_business_available_city').val();
    available_citylist = eval('(' + available_citylist + ')');
    var flag = 0;
    jQuery.each(available_citylist,function(i,val){
        if(val == city_id){
            flag++;
        }
    });
    if(flag > 0){//可以选则主营业务
        jQuery('#tr_main_business').show();
    }else{//主营业务只能是住宅
        jQuery('#mainbusiness_1').click();
        jQuery('#tr_main_business').hide();
    }
}

function display_area_by_store(storeid){
    if(storeid == 0){
        jQuery('#tr_area_2').hide();
        jQuery("#selectareatext").html('请选择区域');
        jQuery("#selectblocktext").html('请选择板块');
        jQuery('#tr_area_1').show();
        return;
    }

    var flag = 0;
    //修改区域板块的默认值
    if(jQuery("#store_area_typeid_"+storeid).val() != 'null'){
        jQuery("#selectareatext").html(jQuery("#store_area_"+storeid).val());
        jQuery("#selectareaid").val(jQuery("#store_area_typeid_"+storeid).val());
        flag++;
    }else{
        jQuery("#selectareatext").html('请选择区域');
        jQuery("#selectareaid").val('');
    }
    if(jQuery("#store_block_typeid_"+storeid).val() != 'null'){
        jQuery("#selectblocktext").html(jQuery("#store_block_"+storeid).val());
        jQuery("#selectblockid").val(jQuery("#store_block_typeid_"+storeid).val());
        flag++;
    }else{
        jQuery("#selectblocktext").html('请选择板块');
        jQuery("#selectblockid").val('');
    }

    //展示动态的区域板块还是静态
    if(flag == 2){
        var static_text = jQuery("#store_area_"+storeid).val() + '-' + jQuery("#store_block_"+storeid).val() + '板块';
        jQuery('#tr_area_1').hide();
        jQuery('#tr_area_2').html('<th style="height:20px;">&nbsp;</th><td colspan="2" style="height:20px;">' + static_text + '</td>').show();
    }else{
        jQuery('#tr_area_2').hide();
        jQuery('#tr_area_1').show();
    }
    jQuery("#areatip .tip_area_1").show();
}

//区域板块恢复初始状态
function reset_area_block(tr1Flag){
    jQuery('#tr_area_2').hide();
    jQuery("#selectareatext").html('请选择区域');
    jQuery("#selectareaid").val('');
    jQuery("#selectblocktext").html('请选择板块');
    jQuery("#selectblockid").val('');
    if(tr1Flag == true){
        jQuery('#tr_area_1').hide();
    }else{
        jQuery('#tr_area_1').show();
    }
    jQuery("#tr_area_1 #areatip").hide();
}

jQuery(document).ready(function () {
	//初始默认城市，兼容火狐、ie
	jQuery("#selectcityid").val(jQuery("#defaultcityid").val());
	jQuery("#selectcityname").val(jQuery("#defaultcityname").val());
    //检查城市是否可以手动选择主营业务
    check_mainbusiness_access();
	jQuery("#areatip").children().hide();
    jQuery.getJSON("/ajkbroker/broker/areainfo/", {"cityid":jQuery("#selectcityid").val(), "act":"area"}, function (json) {
        var str = "";
        jQuery.each(json, function (k, v) {
            str += '<li><a typeid="' + v.typeId + '" href="javascript:void(0);">' + v.typeName + '</a></li>';
		});
		jQuery("#searchlistarea").html(str);
	});
	//手机号码验证
    jQuery("#mobile").blur(function () {
        check_mobile();
	})
    jQuery(".input_text").focus(function () {
		jQuery(this).toggleClass("input_on");
    }).blur(function () {
		jQuery(this).toggleClass("input_on");
	})
	//密码验证
    jQuery("#password").blur(function () {
        check_password();
	})
    jQuery("#checkpwd").blur(function () {
        check_checkpwd();
	})
	//真实姓名
    jQuery("#truename").blur(function () {
        check_truename();
	})
	//城市选择
    jQuery("#select_boxer").click(function () {
		jQuery("#citylist").toggle();
		jQuery("#searchlistblock").hide();
		jQuery("#searchlistarea").hide();
	})
    jQuery("#citytab a").click(function () {
		jQuery(this).siblings().removeClass("on");
		jQuery(this).addClass("on");
        var index = jQuery("#citytab a").index(this);
		jQuery("#citylist ul").hide();
        jQuery("#city" + index).show();
	})
	//城市选中后加载区域
    jQuery("#citylist ul li a").click(function () {
		jQuery("#selectcityname").html(jQuery(this).text());
		jQuery("#selectcityid").val(jQuery(this).attr("cityid"));
		var cityid = jQuery("#selectcityid").val();
		jQuery("#citylist").hide();
        create_area_info(cityid, {});
        check_mainbusiness_access();
        jQuery("#companytip").children().hide();
        reset_area_block(true);
	})
	//区域选择
    jQuery("#select_area").click(function () {
		jQuery("#searchlistarea").toggle();
		jQuery("#selectblocktext").html("请选择板块");
		jQuery("#selectblockid").val(0);
		jQuery("#searchlistblock").hide();
		jQuery("#citylist").hide();
		var cityid = jQuery("#selectcityid").val();
        if (cityid < 11) {
			jQuery("#areatip").children().hide();
			jQuery("#searchlistarea").hide();
			jQuery("#areatip .tip_area_2").show();
		}

	})

    jQuery("#searchlistarea li a").die("click").live("click", function () {
        var typeid = jQuery(this).attr("typeid");
		jQuery("#selectareaid").val(typeid);
		jQuery("#selectareatext").html(jQuery(this).text());
		jQuery("#searchlistarea").hide();
        create_block_info(typeid, {});
	})

    //主营业务检查
    jQuery("input[name='mainbusiness']").change(function(){
        check_mainbusiness();
    });


	//板块查询
    jQuery("#select_block").click(function () {
		jQuery("#searchlistblock").toggle();
		jQuery("#citylist").hide();
		jQuery("#searchlistarea").hide();
		var typeid = jQuery("#selectareaid").val();
        if (typeid < 1) {
			jQuery("#areatip").children().hide();
			jQuery("#searchlistblock").hide();
			jQuery("#areatip .tip_area_3").show();
		}
	})

	//板块选中
    jQuery("#searchlistblock li a").die("click").live("click", function () {
        var typeid = jQuery(this).attr("typeid");
		jQuery("#selectblockid").val(typeid);
		jQuery("#selectblocktext").html(jQuery(this).text());
		jQuery("#searchlistblock").hide();
		jQuery("#areatip").children().hide();
		jQuery("#areatip .tip_area_1").show();
	})
	//公司查询
    jQuery("#companytext").unbind("focus").bind("focus", function () {
        if ( (this.value == "") || (this.value == "输入并选择所属公司") ){
            if(this.value == "输入并选择所属公司"){
                jQuery(this).val("");
            }
            var str = "";
			jQuery("#companyid").val(0);
			jQuery("#storeid").val("");
            str += '<dl class="match"><dt>请输入您所在的公司</dt>'
                + '<dd><a companyid="-1" href="javascript:void(0);">其他公司</a></dd>'
                + '<dd><a companyid="11" href="javascript:void(0);">独立经纪人</a></dd></dl>'
                + '<ul class="searchlist h173"></ul>';
			jQuery("#companylist").html(str);
			jQuery("#companylist").show();
		}
	})
    jQuery("#companytext").unbind("blur").bind("blur", function () {
        if (jQuery("#companyid").val() == 0) {
			jQuery("#companytip").children().hide();
			jQuery("#companytip2").show();
		}
	})
    jQuery("#companytext").unbind("keyup").bind("keyup", function () {
		var cityid = jQuery("#selectcityid").val();
		jQuery("#citylist").hide();
        if (cityid < 11) {
			jQuery("#areatip").children().hide();
			jQuery("#areatip .tip_area_2").show();
		}
		jQuery("#companyid").val(0);
		jQuery("#storeid").val("");
		jQuery("#storename").val("");
        if (this.value != "") {
            jQuery.getJSON("/ajkbroker/broker/areainfo/", {"key":this.value, "act":"company", "cityid":cityid}, function (json) {
                var str = "";
                var str1 = "";
                str += '<dl class="match"><dt>请点击匹配公司</dt>'
                    + '<dd><a companyid="-1" href="javascript:void(0);">其他公司</a></dd>'
                    + '<dd><a companyid="11" href="javascript:void(0);">独立经纪人</a></dd></dl>';
                jQuery.each(json, function (k, v) {
                    str1 += '<li><a companyid="' + v.CompanyId + '" href="javascript:void(0);">' + v.CompanyName + '</a></li>';
				})
                str += '<ul class="searchlist h173">' + str1 + '</ul>';
				jQuery("#companylist").html(str);
				jQuery("#companylist").show();

            });
		}
	})
	//公司选中
    jQuery("#companylist a").die("click").live("click", function () {
        var companyid = jQuery(this).attr("companyid");
		jQuery("#companyid").val(companyid);
		jQuery("#companytext").val(jQuery(this).text());
		jQuery("#companylist").hide();

        if (companyid == -1 || companyid == 11) {
			jQuery("#store").hide();
			jQuery("#storeid").val(0);
            reset_area_block(false);
        } else {
			jQuery("#store").show();
            //隐藏区域板块选择
            jQuery('#tr_area_1').hide();
		}
		jQuery("#companytip").children().hide();
		jQuery("#companytip1").show();
	})
	//门店选中
    jQuery("#storename").unbind("focus").bind("focus", function () {
        if (this.value == "") {
			jQuery("#storeid").val("");
		}
	})
    jQuery("#storename").unbind("blur").bind("blur", function () {
        if (jQuery("#storeid").val() == "") {
			jQuery("#storetip").children().hide();
			jQuery("#storetip .check_error").show();
		}
	})
    jQuery("#storename").unbind("keyup").bind("keyup", function () {
        var companyid = jQuery("#companyid").val();
		var cityid = jQuery("#selectcityid").val();
		var key = this.value;
		jQuery("#storeid").val("");
        if (companyid > 0 && key != "" && cityid > 10) {
            jQuery.getJSON("/ajkbroker/broker/areainfo/", {"companyid":companyid, "act":"store_area", "cityid":cityid, "key":key}, function (json) {
                var str = "";
                jQuery.each(json, function (k, v) {
                    str += '<li><a storeid="' + v.ComanyId + '" href="javascript:void(0);">' + v.ShortCompanyName + '</a>';
                    str += '<input type="hidden" value="' + v.AREA + '" id="store_area_'+ v.ComanyId +'"/>';
                    str += '<input type="hidden" value="' + v.area_typeid + '" id="store_area_typeid_'+ v.ComanyId +'"/>';
                    str += '<input type="hidden" value="' + v.block + '" id="store_block_'+ v.ComanyId +'"/>';
                    str += '<input type="hidden" value="' + v.block_typeid + '" id="store_block_typeid_'+ v.ComanyId +'"/>';
                    str += '</li>';
				});
				str += '<li><a storeid="0" href="javascript:void(0);">其他门店</a></li>'
                str = '<ul class="searchlist h241">' + str + '</ul>';
				jQuery("#shoplist").html(str);
				jQuery("#shoplist").show();
            });
		}
	})

    jQuery("#shoplist a").die("click").live("click", function () {
        var storeid = jQuery(this).attr("storeid");
		jQuery("#storeid").val(storeid);
		jQuery("#storename").val(jQuery(this).text());
		jQuery("#shoplist").hide();
		jQuery("#storetip").children().hide();
		jQuery("#storetip .check_ok").show();

        display_area_by_store(storeid);
	})

    jQuery("#checkbox_xy").click(function () {
        if (jQuery("#checkbox_xy").attr("checked") == "checked") {
			jQuery("#checktip2").hide();
			jQuery("#checktip1").show();
        } else {
			jQuery("#checktip1").hide();
			jQuery("#checktip2").show();
		}
	})
	//绑定释放手机号码
    jQuery("#mb_mobile_1").click(showReleaseDialog);
    jQuery("#mb_mobile_2").click(showReleaseDialog);
    //展示释放上手机号码提示框
    function showReleaseDialog(){
        jQuery("#msgtips").dialog({
            bgiframe:true,
            resizable:false,
            width:434,
            overlay:{
                backgroundColor:'#000',
                opacity:0.5
            }
        });
        jQuery("#msgtips").dialog('open');
    }

	//绑定关闭窗口
    jQuery(".close_tip_ql").click(function () {
		jQuery("#msgtips").dialog("close");

	})
    jQuery("#btn_jx").click(function () {
		var mobile = jQuery("#mobile").val();
        var url = jQuery(this).attr("kname") + "QQmobilesecZ" + mobile;
        window.location.href = url;
		return false;
	})

    jQuery("#findpwd").click(function () {
		var mobile = jQuery("#mobile").val();
        var url = jQuery(this).attr("kname") + "QQmobilesecZ" + mobile;
        window.location.href = url;
		return false;
	})

})

function valid_ql() {
    if (jQuery("#checkbox_xy").attr("checked") != "checked") {
		jQuery("#checktip1").hide();
		jQuery("#checktip2").show();
		return false;
	}
	var check = true;
    if (!pregflag_mobile) {
		jQuery("#mobile").blur();
        //return false;
        check =  false;
	}
    if (!pregflag_pwd) {
		jQuery("#password").blur();
		//return false;
        check =  false;
	}
    if (!pregflag_chepwd) {
		jQuery("#checkpwd").blur();
		//return false;
        check =  false;
	}

    if (!pregflag_truename) {
		jQuery("#truename").blur();
		//return false;
        check =  false;
	}

    if (jQuery("#selectareaid").val() == 0) {
        jQuery("#areatip").show();
		jQuery("#areatip").children().hide();
		jQuery("#areatip .tip_area_3").show();
		//return false;
        check =  false;
	}

    if (jQuery("#selectblockid").val() == 0) {
        jQuery("#areatip").show();
		jQuery("#areatip").children().hide();
		jQuery("#areatip .tip_area_4").show();
		//return false;
        check =  false;
    } else {
		jQuery("#areatip").children().hide();
	}

    if(jQuery("#selectblockid").val() != 0 && jQuery("#selectareaid").val() != 0){
        jQuery("#areatip .tip_area_1").show();
    }

    if (jQuery("#companyid").val() == 0) {
		jQuery("#companytip").children().hide();
		jQuery("#companytip2").show();
		//return false;
        check =  false;
	}

    if (jQuery("#storeid").val() == "") {
		jQuery("#storetip").children().hide();
		jQuery("#storetip .check_error").show();
		//return false;
        check =  false;
	}

    if(check_mainbusiness() === false){
        jQuery('#business_tips').show();
        jQuery('#tip_business_ok').hide('');
        jQuery('#tip_business_error').show('');
        jQuery('#tip_business_error').html('请选择主营业务');
        check =  false;
    }else{
        jQuery('#business_tips').show();
        jQuery('#tip_business_error').hide('');
        jQuery('#tip_business_ok').show('');
    }


    if (!check) {
		return false;
	}
	return true;
}