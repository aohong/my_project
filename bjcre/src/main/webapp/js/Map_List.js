var headerSearchForm = $("#HeaderSearchForm");
var headerSearchText = $("#HeaderSearchText");
var headerSearchButton = $("#HeaderSearchButton");
$("[placeholders]").focus(function () {
    var a = $(this);
    if (a.val() == a.attr("placeholders")) {
        a.val("");
        a.removeClass("placeholder")
    }
}).blur(function () {
    var a = $(this);
    if (a.val() == "" || a.val() == a.attr("placeholders")) {
        a.addClass("placeholder");
        a.val(a.attr("placeholders"))
    }
}).blur();
$("[placeholders]").parents("form").submit(function () {
    $(this).find("[placeholders]").each(function () {
        var a = $(this);
        if (a.val() == a.attr("placeholders")) {
            a.val("")
        }
    })
});
headerSearchButton.click(function () {
    setTimeout(function () {
        headerSearchForm.submit()
    }, 100);
    return false
});
$(".search-box .submit-box").bind({
    mouseover: function () {
        $(this).addClass("search-button-over")
    }, mouseout: function () {
        $(this).removeClass("search-button-over")
    }
});
headerSearchForm.bind("keydown", function (a) {
    if (a.which == "13") {
        setTimeout(function () {
            headerSearchForm.submit()
        }, 50);
        return false
    }
});
headerSearchForm.submit(function () {
    if (headerSearchText.val().length > 15) {
        alert("请勿超过15个中文字符");
        return false
    } else {
        return true
    }
});
$("#HeaderSearchText").bind({
    mouseover: function () {
        if ($(this).val() != "" && $(this).val() != $(this).attr("placeholders")) {
            $(".serch_box_close").css("display", "")
        }
        $("#HeaderSearchText").addClass("search_bg_on")
    }, mouseleave: function () {
        $("#HeaderSearchText").removeClass("search_bg_on");
        $("#HeaderSearchText").addClass("search_bg")
    }, focus: function () {
        $("#HeaderSearchText").addClass("search_bg_onfocus");
        if ($(this).val() != "" && $(this).val() != $(this).attr("placeholders")) {
            $(".serch_box_close").css("display", "")
        } else {
            $(".serch_box_close").hide()
        }
    }, blur: function () {
        $("#HeaderSearchText").removeClass("search_bg_onfocus")
    }, keyup: function () {
        if ($(this).val() != "" && $(this).val() != $(this).attr("placeholders")) {
            $(".serch_box_close").css("display", "")
        } else {
            $(".serch_box_close").hide()
        }
    }
});
$(".serch_box_close").bind("click", function () {
    $("#HeaderSearchText").val("");
    $(this).hide();
    $("#HeaderSearchText").focus()
});
$("#HeaderSearchText").autocomplete("/ajax/searchsuggestion", {
    resultsClass: "search_cont",
    delay: 0,
    width: 297,
    scroll: false,
    selectFirst: false,
    max: 14,
    extraParams: {c: $("#currentcityid").val(), t: $("#suggestiontype").val()},
    parse: function (rst) {
        if (rst) {
            var data = eval("(" + rst + ")");
            var parsed = [];
            var rows = [];
            for (var i = 0; i < data.length; i++) {
                rows[i] = data[i]
            }
            for (var i = 0; i < rows.length; i++) {
                var row = rows[i];
                if (row) {
                    parsed[parsed.length] = {data: row, value: row.keyword, result: row.keyword}
                }
            }
            return parsed
        }
    },
    formatItem: function (d) {
        if (d.keyword && d.records) {
            if (d.is_new == 1) {
                var a = $("<li></li>"), c = $('<span class="search_ad">' + d.keyword + "</span>"), b = $('<span class="search_num">开发商直供</span>');
                a.append(c).append(b);
                return a.html()
            } else {
                var a = $("<li></li>"), c = $('<span class="search_ad">' + d.keyword + "</span>");
                var b = "";
                if ($("#suggestiontype").val() != 6) {
                    b = $('<span class="search_num"><b>' + d.records + "</b>套</span>")
                }
                a.append(c).append(b);
                return a.html()
            }
        }
        return false
    }
});
$("#HeaderSearchText").bind("focus", function () {
    $(this).addClass("over")
});
function dw_add_link_from(b) {
    var h = /^http:\/\/./;
    var c = document.getElementsByTagName("a");
    for (var g = 0; g < c.length; g++) {
        var k = c[g].getAttribute(b);
        if (k != null) {
            var f = c[g].href;
            if (!f.match(h)) {
                continue
            }
            if (f.indexOf("from=") != -1) {
                continue
            }
            k = encodeURIComponent(k);
            var e = f.split("#");
            if (f.indexOf("?") != -1) {
                f = e[0] + "&from=" + k
            } else {
                f = e[0] + "?from=" + k
            }
            if (e.length > 1) {
                for (var d = 1; d < e.length; d++) {
                    f += "#" + e[d]
                }
            }
            c[g].href = " " + f
        }
    }
}
$(document).bind("ready", function () {
    dw_add_link_from("_soj")
});
if (typeof jQuery != "undefined") {
    jQuery.fn.extend({
        Oselect: function (a) {
            if (this.length == 0 || this.length > 1) {
                return false
            }
            var o = {
                CLASSNAME: "Oselect",
                DEFAULTCLASSNAME: "defaults",
                TITLECLASSNAME: "title",
                OVERCLASSNAME: "over",
                ULCLASSNAME: "ul",
                LICLASSNAME: "ul-li",
                LIOVERCLASSNAME: "ul-li-over",
                LIEMPTYCLASSNAME: "ul-li-empty",
                LIEMPTYINNERHTML: "",
                LIEMPTYTITLE: "",
                LICURSELECTCLASSNAME: "ul-li-cur",
                ISMOUSESHOW: true,
                ONFOCUS: function () {
                },
                ONBLUR: function () {
                },
                ONCHANGE: function () {
                }
            };
            for (var f in a) {
                o[f] = a[f]
            }
            var j = this[0], k = "", c, p = j.getElementsByTagName("option"), e = document.createElement("div"), g = document.createElement("a"), m = document.createElement("ul"), l, f;
            e.className = o.CLASSNAME + "-box " + o.DEFAULTCLASSNAME;
            g.className = o.CLASSNAME + "-" + o.TITLECLASSNAME;
            m.className = o.CLASSNAME + "-" + o.ULCLASSNAME;
            e.appendChild(g);
            e.appendChild(m);
            var n = $(e);
            if (o.ISMOUSESHOW) {
                n.bind("mouseover", function () {
                    if (o.ONFOCUS.call(this) === false) {
                        return false
                    }
                    var i = $(m);
                    n.addClass(o.CLASSNAME + "-" + o.OVERCLASSNAME);
                    i.show()
                });
                n.bind((document.attachEvent ? "mouseleave" : "mouseout"), function () {
                    n.removeClass(o.CLASSNAME + "-" + o.OVERCLASSNAME);
                    $(m).hide()
                });
                n.bind("focus", function () {
                    $(m).show()
                });
                n.bind("blur", function () {
                    $(m).hide()
                })
            }
            function h(i, q) {
                i.bind("mouseover", function () {
                    $(this).addClass(o.CLASSNAME + "-" + o.LIOVERCLASSNAME)
                });
                i.bind("mouseout", function () {
                    $(this).removeClass(o.CLASSNAME + "-" + o.LIOVERCLASSNAME)
                });
                i.bind("click", function () {
                    for (var r = 0; r < p.length; r++) {
                        p[r].removeAttribute("selected")
                    }
                    if (q !== c) {
                        o.ONCHANGE.call(this)
                    }
                    q.setAttribute("selected", "selected");
                    c = q;
                    k = q.value;
                    g.innerHTML = q.innerHTML;
                    g.setAttribute("v", q.value);
                    m.style.display = "none";
                    var s = $(this);
                    s.prevAll("li").removeClass(o.CLASSNAME + "-" + o.LICURSELECTCLASSNAME);
                    s.nextAll("li").removeClass(o.CLASSNAME + "-" + o.LICURSELECTCLASSNAME);
                    $(this).addClass(o.CLASSNAME + "-" + o.LICURSELECTCLASSNAME);
                    n.removeClass(o.CLASSNAME + "-" + o.OVERCLASSNAME)
                })
            }

            if (p.length == 0) {
                l = document.createElement("li");
                l.innerHTML = o.LIEMPTYINNERHTML;
                l.className = o.CLASSNAME + "-" + o.LIEMPTYCLASSNAME;
                m.appendChild(l);
                g.innerHTML = o.LIEMPTYTITLE
            }
            for (f = 0; f < p.length; f++) {
                l = document.createElement("li");
                l.className = o.CLASSNAME + "-" + o.LICLASSNAME;
                l.innerHTML = p[f].innerHTML;
                l.setAttribute("v", p[f].value);
                m.appendChild(l);
                var d = $(l);
                h(d, p[f]);
                if (p[f].selected) {
                    c = p[f];
                    k = p[f].value;
                    g.innerHTML = p[f].innerHTML;
                    g.setAttribute("v", p[f].value)
                }
            }
            var b = $(j);
            b.hide();
            b.after(e);
            return {
                additem: function (t, u) {
                    var s = document.createElement("option"), r = p.length == 0 ? true : false;
                    s.value = t.value;
                    s.innerHTML = t.title;
                    j.appendChild(s);
                    l = document.createElement("li");
                    l.className = o.CLASSNAME + "-" + o.LICLASSNAME;
                    l.innerHTML = t.title;
                    l.setAttribute("v", t.value);
                    m.appendChild(l);
                    p = j.getElementsByTagName("option");
                    for (var q = 0; q < p.length; q++) {
                        p[q].selected = false
                    }
                    var v = $(l);
                    h(v, s);
                    if (u || r) {
                        c = s;
                        k = t.value;
                        g.innerHTML = t.title;
                        g.setAttribute("v", t.value);
                        s.selected = "selected"
                    }
                    $(m).find("." + o.CLASSNAME + "-" + o.LIEMPTYCLASSNAME).remove()
                }, selectitem: function (r) {
                    for (var s = 0; s < p.length; s++) {
                        p[s].removeAttribute("selected")
                    }
                    if (this.length() - 1 < r || r < 0) {
                        g.innerHTML = o.LIEMPTYTITLE;
                        k = null;
                        c = null;
                        return true
                    }
                    p[r] && p[r].setAttribute("selected", "selected");
                    c = p[r];
                    k = p[r].value;
                    var q = m.getElementsByTagName("li")[r], t;
                    if (q) {
                        t = $(q);
                        t.prevAll("li").removeClass(o.CLASSNAME + "-" + o.LICURSELECTCLASSNAME);
                        t.nextAll("li").removeClass(o.CLASSNAME + "-" + o.LICURSELECTCLASSNAME);
                        t.addClass(o.CLASSNAME + "-" + o.LICURSELECTCLASSNAME);
                        g.innerHTML = q.innerHTML;
                        g.setAttribute("v", q.getAttribute("v"))
                    }
                }, getitem: function (i) {
                    return m.getElementsByTagName("li")[i]
                }, length: function () {
                    return p.length
                }, clear: function () {
                    j.innerHTML = "";
                    c = null;
                    k = null;
                    g.innerHTML = o.LIEMPTYTITLE;
                    g.removeAttribute("v");
                    m.innerHTML = "";
                    var i = document.createElement("li");
                    i.innerHTML = o.LIEMPTYINNERHTML;
                    i.className = o.CLASSNAME + "-" + o.LIEMPTYCLASSNAME;
                    m.appendChild(i);
                    return true
                }, delitem: function (r) {
                    if (p.length < r + 1) {
                        return false
                    }
                    var s = p[r].selected ? true : false;
                    j.removeChild(p[r]);
                    var q = m.getElementsByTagName("li");
                    m.removeChild(q[r]);
                    p = j.getElementsByTagName("option");
                    if (s) {
                        if (p.length == 0) {
                            var i = document.createElement("li");
                            i.className = o.CLASSNAME + "-" + o.LIEMPTYCLASSNAME;
                            m.appendChild(i);
                            g.innerHTML = "";
                            return true
                        }
                        if (p.length == 1) {
                            c = p[0];
                            k = p[0].value;
                            g.innerHTML = p[0].innerHTML;
                            g.setAttribute("v", p[0].value);
                            p[0].selected = "selected";
                            return true
                        }
                        if (p.length > r) {
                            c = p[r];
                            k = p[r].value;
                            g.innerHTML = p[r].innerHTML;
                            g.setAttribute("v", p[r].value);
                            p[r].selected = "selected"
                        } else {
                            c = p[p.length - 1];
                            k = p[p.length - 1].value;
                            g.innerHTML = p[p.length - 1].innerHTML;
                            g.setAttribute("v", p[p.length - 1].value);
                            p[p.length - 1].selected = "selected"
                        }
                        return true
                    }
                }, getValue: function () {
                    return k
                }, getThisObj: function () {
                    return e
                }
            }
        }
    })
}
var baiduMap = function (b, a) {
    return new BMap.Map(b, a)
};
var googleMap = function (a) {
    return new GMap2(a)
};
var JinpuMap = function (c, b, a) {
    this.mapType = b;
    this.mapArea = c;
    if (this.mapType == "baidu") {
        this.map = baiduMap(c, a)
    } else {
        this.map = new googleMap(c)
    }
};
JinpuMap.prototype = {
    Point: function (c, b) {
        if (this.mapType == "baidu") {
            var a = new BMap.Point(b, c)
        } else {
            var a = new GLatLng(c, b)
        }
        return a
    }, Pixel: function (a, c) {
        if (this.mapType == "baidu") {
            var b = new BMap.Pixel(a, c)
        } else {
            var b = new GPoint(a, c)
        }
        return b
    }, setCenter: function (a, b) {
        if (this.mapType == "baidu") {
            this.map.centerAndZoom(a, b);
            this.map.setCenter(a)
        } else {
            this.map.setCenter(a, b)
        }
    }, addControl: function (a) {
        if (this.mapType == "baidu") {
            this.map.addControl(a)
        } else {
            this.map.addControl(a)
        }
    }, scaleControl: function () {
        if (this.mapType == "baidu") {
            this.addControl(new BMap.ScaleControl())
        } else {
            this.addControl(new GScaleControl())
        }
    }, largeMapControl: function () {
        if (this.mapType == "baidu") {
            this.addControl(new BMap.NavigationControl())
        } else {
            this.addControl(new GLargeMapControl())
        }
    }, overviewMapControl: function () {
        if (this.mapType == "baidu") {
            this.addControl(new BMap.OverviewMapControl())
        } else {
            this.addControl(new GOverviewMapControl())
        }
    }, enableScrollWheelZoom: function () {
        if (this.mapType == "baidu") {
            this.map.enableScrollWheelZoom()
        } else {
            this.map.enableScrollWheelZoom()
        }
    }, addListener: function (b, a) {
        if (this.mapType == "baidu") {
            this.map.addEventListener(b, a)
        } else {
            GEvent.addListener(this.map, b, a)
        }
    }, pointToPixel: function (a) {
        if (this.mapType == "baidu") {
            return this.map.pointToPixel(a)
        } else {
            return this.map.fromLatLngToContainerPixel(a)
        }
    }, pointToOverlayPixel: function (a) {
        if (this.mapType == "baidu") {
            var b = this.map.pointToOverlayPixel(a)
        } else {
            var b = this.map.fromLatLngToDivPixel(a)
        }
        return b
    }, pixelToPoint: function (b) {
        if (this.mapType == "baidu") {
            var a = this.map.pixelToPoint(b)
        } else {
            var a = this.map.fromContainerPixelToLatLng(b)
        }
        return a
    }, getBounds: function () {
        if (this.mapType == "baidu") {
            return this.map.getBounds()
        } else {
            return this.map.getBounds()
        }
    }, getLat: function (a) {
        if (this.mapType == "baidu") {
            return a.lat
        } else {
            return a.lat()
        }
    }, getLng: function (a) {
        if (this.mapType == "baidu") {
            return a.lng
        } else {
            return a.lng()
        }
    }, setZoom: function (a) {
        if (this.mapType == "baidu") {
            return this.map.zoomTo(a)
        } else {
            return this.map.setZoom(a)
        }
    }, setGoogleZoomLevel: function (b, a) {
        if (this.mapType == "baidu") {
            return
        } else {
            G_NORMAL_MAP.getMinimumResolution = function () {
                return b
            };
            G_NORMAL_MAP.getMaximumResolution = function () {
                return a
            }
        }
    }, setGoogleSatellite: function (b, a) {
        G_HYBRID_MAP.getMinimumResolution = function () {
            return b
        };
        G_HYBRID_MAP.getMaximumResolution = function () {
            return a
        }
    }, getPanes: function (a) {
        if (this.mapType == "baidu") {
            return this.map.getPanes()
        } else {
            return this.map.getPane(a)
        }
    }, getMarkerPane: function () {
        if (this.mapType == "baidu") {
            var a = this.getPanes();
            return a.markerPane
        } else {
            var a = this.getPanes(G_MAP_MARKER_PANE);
            return a
        }
    }, localSearch: function (c, a) {
        if (this.mapType == "baidu") {
            var b = new BMap.LocalSearch(c, a)
        } else {
            var b = new google.search.LocalSearch()
        }
        return b
    }
};
EstateMarker.TYPE = {
    0: {className: "mansion-tip"},
    1: {className: "mansion-cur-tip"},
    2: {className: "area-tip"},
    3: {className: "area-cur-tip"}
};
EstateMarker.prototype = new BMap.Overlay();
function EstateMarker(a) {
    var b = this;
    this._options = {
        center: {},
        type: 0,
        title: "",
        title2: "",
        istitle2: true,
        className: "",
        id: 1,
        onclick: function () {
        },
        onmouseover: function () {
        },
        onmouseout: function () {
        },
        settitle: function (c) {
            this.title = c;
            $(b._markerNode).find(".mtitle").text(c)
        },
        settitle2: function (c) {
            this.title2 = c;
            $(b._markerNode).find(".mtitle2").text(c)
        }
    };
    this._markerNode = null;
    this._titleNode = null;
    this._title2Node = null;
    this.setOption(a)
}
EstateMarker.prototype.initialize = function (a) {
    this._map = a;
    this.createMarker();
    a.getPanes().markerPane.appendChild(this._markerNode);
    return this._markerNode
};
EstateMarker.prototype.draw = function () {
    var a = this._map.pointToOverlayPixel(this._options.center);
    this._markerNode.style.left = a.x + "px";
    this._markerNode.style.top = (a.y - this._markerNode.clientHeight) + "px"
};
EstateMarker.prototype.show = function () {
    if (this._markerNode) {
        this._markerNode.style.display = ""
    }
};
EstateMarker.prototype.hide = function () {
    if (this._markerNode) {
        this._markerNode.style.display = "none"
    }
};
EstateMarker.prototype.remove = function () {
    if (this._markerNode) {
        $(this._markerNode).remove()
    }
};
EstateMarker.prototype.showTitle2 = function () {
    if (this._title2Node) {
        this._title2Node.style.display = ""
    }
};
EstateMarker.prototype.hideTitle2 = function () {
    if (this._title2Node) {
        this._title2Node.style.display = "none"
    }
};
EstateMarker.prototype.switchType = function (b) {
    this._options.type = b;
    if (EstateMarker.TYPE[b]) {
        for (var a in EstateMarker.TYPE[b]) {
            switch (a) {
                case"className":
                    this._options.className = EstateMarker.TYPE[b][a];
                    this._markerNode.className = EstateMarker.TYPE[b][a];
                    break
            }
        }
    }
};
EstateMarker.prototype.getOptions = function () {
    return this._options
};
EstateMarker.prototype.setOption = function (a) {
    for (var b in a) {
        this._options[b] = a[b]
    }
    if (!a.type || !EstateMarker.TYPE[a.type]) {
        a.type = 0
    }
    for (var b in EstateMarker.TYPE[a.type]) {
        this._options[b] = EstateMarker.TYPE[a.type][b]
    }
};
EstateMarker.prototype.createMarker = function () {
    var b = document.createElement("div"), a = document.createElement("div"), f = document.createElement("div"), h = document.createElement("div"), g = document.createElement("div"), e = this._options;
    b.id = e.id;
    b.className = e.className;
    a.className = "mright";
    f.className = "mleft";
    h.className = "mtitle";
    h.innerHTML = e.title;
    g.className = "mtitle2";
    g.innerHTML = e.title2;
    f.appendChild(h);
    f.appendChild(g);
    b.appendChild(f);
    b.appendChild(a);
    this._markerNode = b;
    this._titleNode = h;
    this._title2Node = g;
    var d = $(b), c = this;
    d.bind("click", function () {
        return e.onclick.call(c)
    });
    d.bind("mouseover", function () {
        return e.onmouseover.call(c)
    });
    d.bind((document.attachEvent ? "mouseleave" : "mouseout"), function () {
        return e.onmouseout.call(c)
    });
    if (!e.istitle2) {
        g.style.display = "none"
    }
    return this._markerNode
};
var JinpuMapSearch = function (c, b) {
    this.mapType = "baidu", this.mapArea = "jmap", this.DEF_CITY_INFO = c, this.curr_lat = c.lat;
    this.curr_lng = c.lng;
    this.curr_zoom = c.zoom, this.mapOptions = {minZoom: 10, maxZoom: 18}, this.filter = b;
    this.buildingFilter = null;
    this.keyword = b.keyword;
    this.buildingId = 0;
    this.kwbuildingId = 0;
    this.currentMarker = null;
    this.lastMarker = null;
    this.mapMarkers = {};
    this.mapDistrictMarkers = {};
    this.kwMarkers = {};
    this.housetype = b.housetype;
    this.tradetype = b.tradetype;
    this.phousetype = this.housetype == 1 ? "xiezilou" : "shangpu";
    this.ptradetype = this.tradetype == 1 ? "shou" : "zu";
    this.drawMarkerHandler = [];
    var d = this;
    JMAP.bindurlchange(function () {
        d.urlChange()
    });
    this.infowin_officenum = $("#officenum");
    this.infowin_houselist_ul = $(".houselist");
    this.infowin_houselist_box = $(".houselistbox");
    this.infowin_page = $(".houselistbox .nav");
    this.infowin_title = $(".houselistbox h2");
    this.infowin_builing_price = $("#buildingprice");
    this.infowin_builing_price_box = $("#buildingprice_box");
    this.infowin_emptyinfo_box = $(".emptyinfo-box");
    this.infowin_leftcond = $(".lecond");
    this.infowin_sortlink = $(".hcbutton a");
    this.infowin_sortspan = $(".hcbutton");
    this.selhq = $("#selhq");
    this.map_mask = $(".mask2");
    this.map_box = $("#map-box");
    this.map_fruitless_box = $(".fruitless-box");
    this.map_fruitless_box2 = $(".fruitless-box2");
    this.HeaderSearchText = $("#HeaderSearchText");
    this.btnKWSearchSubmit = $("#isubmit");
    this.frmHeaderSearch = $("#HeaderSearchForm");
    this.cur_keyword = $("#cur_keyword");
    this.listMode = $(".listmode a");
    this.loupanLink = $(".more-house a");
    var a = "请输入楼盘名、商圈、路名...";
    this.frmHeaderSearch.submit(function () {
        var e = d.HeaderSearchText.val();
        if (e != a && e) {
            $.ajax({
                type: "get",
                url: "/ajax/building/match_building?name=" + encodeURIComponent(e) + "&cityid=" + c.cityId + "&htype=" + d.housetype + "&ttype=" + d.tradetype,
                dataType: "json",
                success: function (f) {
                    if (f.id != undefined) {
                        JMAP.initialBlockSelect();
                        JMAP.initialDistrictSelect();
                        $('.option a[value="{p8:0}"]').trigger("click");
                        $('.option a[value="{p9:0}"]').trigger("click");
                        $('.option a[value="{p10:0}"]').trigger("click");
                        $('.option a[value="{p11:0}"]').trigger("click");
                        d.search = {};
                        d.mapCenter(f.blat, f.blng, 16);
                        d.kwbuildingId = f.id
                    } else {
                        d.bLocalSearch(e)
                    }
                }
            })
        }
        return false
    });
    $(".houselistbox .close a").click(function () {
        d.hidePropWindow();
        return false
    });
    $(".lecond .lesel a").click(function () {
        var e = "";
        if (this.name == "info_p8") {
            e = "p8"
        } else {
            if (this.name == "info_p9") {
                e = "p9"
            } else {
                if (this.name == "info_p11") {
                    e = "p11"
                }
            }
        }
        if (e != "") {
            var f = this;
            $(f).parents(".lesel").find("a").removeClass("cur");
            $(f).addClass("cur");
            d.buildingFilter[e] = $(f).attr("value");
            d.buildingFilter.log = 1;
            d.buildingFilter.cs = this.name;
            d.showPropWindow(d.buildingId, 1)
        }
        return false
    });
    this.selhq.click(function () {
        var e = this.checked ? 1 : 0;
        d.buildingFilter.i1 = e;
        d.buildingFilter.log = 1;
        d.buildingFilter.cs = "is_hq";
        d.showPropWindow(d.buildingId, 1)
    });
    this.infowin_sortlink.click(function () {
        var g = $(this);
        var i = g.attr("value");
        var f = 1;
        var h = "up";
        var e = "up";
        if (i == 1) {
            e = "up";
            f = 2;
            h = "down"
        }
        if (i == 2) {
            e = "down";
            f = 1;
            h = "up"
        }
        d.infowin_sortspan.removeClass("hccur");
        d.infowin_sortlink.removeClass("down");
        d.infowin_sortlink.addClass("up");
        g.attr("value", f);
        g.removeClass(e);
        g.addClass(h);
        g.parent().addClass("hccur");
        if (this.id == "sortbyarea") {
            d.buildingFilter.i2 = f;
            d.buildingFilter.i3 = 0
        }
        if (this.id == "sortbyprice") {
            d.buildingFilter.i3 = f;
            d.buildingFilter.i2 = 0
        }
        d.buildingFilter.log = 1;
        d.buildingFilter.cs = this.id;
        d.showPropWindow(d.buildingId, 1);
        return false
    });
    $(".iknow").click(function () {
        $(this).parents(".fruitless-contain").hide()
    })
};
JinpuMapSearch.prototype = {
    drawMap: function () {
        this.jmap = new JinpuMap(this.mapArea, this.mapType, this.mapOptions);
        var a = this.jmap.Point(this.DEF_CITY_INFO.lat, this.DEF_CITY_INFO.lng);
        this.jmap.setCenter(a, this.curr_zoom);
        this.jmap.map.enableScrollWheelZoom();
        this.jmap.scaleControl();
        this.jmap.largeMapControl();
        this.jmap.overviewMapControl();
        this.markerPanel = this.jmap.getMarkerPane();
        var b = this;
        this.jmap.addListener("zoomend", function () {
            b.zoomEnd()
        });
        this.jmap.addListener("movestart", function () {
            b.moveStart()
        });
        this.jmap.addListener("moveend", function () {
            b.moveEnd()
        });
        return this.jmap
    }, mapZoom: function (a) {
        this.jmap.setZoom(a)
    }, mapPan: function (c, a, b) {
        this.jmap.map.panTo(this.jmap.Point(c, a));
        if (b != undefined) {
            this.jmap.setZoom(b)
        }
    }, mapCenter: function (d, b, c) {
        var a = this.jmap.Point(d, b);
        this.jmap.setCenter(a, c)
    }, zoomEnd: function () {
        window.clearTimeout(this.timeoutHandle);
        var a = this;
        if (this.mapType == "baidu") {
            a.removeMapMarkers();
            this.timeoutHandle = window.setTimeout(function () {
                a.getJsonMarkers()
            }, 800)
        }
    }, moveStart: function () {
        window.clearTimeout(this.timeoutHandle)
    }, moveEnd: function () {
        var a = this;
        this.timeoutHandle = window.setTimeout(function () {
            a.getJsonMarkers()
        }, 800)
    }, bLocalSearch: function (a) {
        var c = this;
        var b = this.jmap.localSearch(this.jmap.map);
        b.setPageCapacity(10);
        b.enableAutoViewport();
        b.setSearchCompleteCallback(function (f) {
            var e = f.getPoi(0);
            if (e) {
                JMAP.initialBlockSelect();
                JMAP.initialDistrictSelect();
                c.search = {};
                c.search.lat = e.point.lat;
                c.search.lng = e.point.lng;
                c.search.len = f.getNumPois();
                c.keyword = a;
                c.kwbuildingId = 0;
                var d = c.jmap.Point(c.search.lat, c.search.lng);
                c.jmap.setCenter(d, 16);
                c.buildKWMarkers()
            } else {
                c.removeKWMarkers();
                c.cur_keyword.text(a);
                c.map_fruitless_box2.show()
            }
        });
        b.search(a)
    }, removeMapMarkers: function () {
        this.kwMarkers = {};
        this.mapMarkers = {};
        this.mapDistrictMarkers = {};
        this.jmap.map.clearOverlays()
    }, removeBuildingMarkers: function () {
        for (var b in this.drawMarkerHandler) {
            window.clearTimeout(this.drawMarkerHandler[b])
        }
        for (var a in this.mapMarkers) {
            if (this.mapMarkers[a] != null) {
                this.mapMarkers[a].remove();
                delete this.mapMarkers[a]
            }
        }
        var d = this;
        var c = $(this.jmap.getPanes().markerPane).find(".mansion-tip").each(function () {
            if (this.id > 0 && this.id != d.kwbuildingId) {
                $(this).remove()
            }
        })
    }, removeDistrictMarkers: function () {
        for (var a in this.mapDistrictMarkers) {
            if (this.mapDistrictMarkers[a] != null) {
                this.mapDistrictMarkers[a].remove();
                delete this.mapDistrictMarkers[a]
            }
        }
    }, removeKWMarkers: function () {
        for (var a in this.kwMarkers) {
            if (this.kwMarkers[a] != null) {
                this.kwMarkers[a].remove();
                delete this.kwMarkers[a]
            }
        }
    }, getBounds: function () {
        var e = 80;
        var g = this.jmap.pixelToPoint(this.jmap.Pixel(e, e / 2));
        var d = this.jmap.pixelToPoint(this.jmap.Pixel(this.map_box.width() - e / 2, this.map_box.height() - e / 2));
        var b = this.jmap.getLat(d).toFixed(3);
        var a = this.jmap.getLat(g).toFixed(3);
        var f = this.jmap.getLng(g).toFixed(3);
        var c = this.jmap.getLng(d).toFixed(3);
        this.bounds = {slatFrom: b, slatTo: a, slngFrom: f, slngTo: c}
    }, buildListUrl: function () {
        var url = "/" + this.ptradetype + "/";
        if (JMAP.region != undefined) {
            var districtValue = JMAP.region.getValue();
            if (districtValue == null) {
                districtValue = JMAP.area.getValue()
            }
            if (districtValue != null) {
                var pinyin = eval("(" + districtValue + ")").pinyin;
                url += pinyin + "/"
            }
        }
        var params = [];
        if (this.filter.p8 > 0) {
            params.push("ai=" + this.filter.p8)
        }
        if (this.filter.p9 > 0) {
            params.push("mr=" + this.filter.p9)
        }
        if (this.filter.p10 > 0) {
            params.push("dr=" + this.filter.p10)
        }
        if (this.filter.p11 > 0) {
            params.push("tp=" + this.filter.p11)
        }
        if (params.length > 0) {
            url += "?" + params.join("&")
        }
        if (url != this.listModeUrl) {
            this.listModeUrl = url;
            this.listMode.attr("href", url)
        }
    }, buildUrl: function (k, e, j, l) {
        var b = this.filter;
        if (j == 3) {
            b = l
        }
        this.getBounds();
        var g = this.jmap.map.getCenter();
        var h = this.jmap.map.getZoom();
        var a = [];
        var f = {
            s1: j,
            p2: this.housetype,
            p3: this.tradetype,
            p4: this.bounds.slatFrom,
            p5: this.bounds.slatTo,
            p6: this.bounds.slngFrom,
            p7: this.bounds.slngTo,
            p8: b.p8,
            p9: b.p9,
            p10: b.p10,
            p11: b.p11,
            p12: k,
            p13: e,
            p15: 1,
            p16: 1,
            p17: this.curr_lat,
            p18: this.curr_lng,
            p19: this.jmap.getLat(g),
            p20: this.jmap.getLng(g),
            p21: this.curr_zoom,
            p22: h,
            p30: this.mapType
        };
        if (j == 3 && (b.i1 != undefined)) {
            f.i1 = b.i1
        }
        if (j == 3 && (b.i2 != undefined)) {
            f.i2 = b.i2;
            f.i3 = b.i3
        }
        if (j == 3 && b.log != undefined && b.cs != undefined) {
            f.log = b.log;
            f.cs = b.cs;
            delete b.log;
            delete b.cs
        }
        if (this.kwbuildingId > 0) {
            f.k1 = this.kwbuildingId
        }
        for (var d in f) {
            a.push(d + "=" + f[d])
        }
        a = a.join("&");
        if (k > 0 && j == 3) {
            return a
        } else {
            JMAP.URL.setUrl({s1: j});
            location.hash = a;
            this.curr_lat = this.jmap.getLat(g);
            this.curr_lng = this.jmap.getLng(g);
            this.curr_zoom = h
        }
    }, urlChange: function () {
        var a = "/map/search/?" + location.hash.replace(/#/, "");
        var d = JMAP.URL.getparam();
        var c = this.filter;
        c.p8 = d.p8;
        c.p9 = d.p9;
        c.p10 = d.p10;
        c.p11 = d.p11;
        this.filter = c;
        this.buildListUrl();
        var e = this;
        this.hidePropWindow();
        if (d.s1 == 1) {
            $.ajax({
                type: "get", url: a, dataType: "json", success: function (f) {
                    e.buildKWMarkers();
                    e.buildingnum = f.length;
                    if (e.buildingnum == 0) {
                        e.removeBuildingMarkers();
                        e.removeDistrictMarkers();
                        if (!document.getElementById("noremind").checked) {
                            e.map_fruitless_box.show()
                        }
                    } else {
                        e.showBuildingMarkers(f)
                    }
                }
            });
            return
        }
        if (d.s1 == 2) {
            this.removeBuildingMarkers();
            var b = function (i) {
                if (this.mapDistrictMarkers[i.id] == undefined || this.mapDistrictMarkers[i.id] == null) {
                    var j = this.jmap.Point(i.blat, i.blng);
                    var g = this.buildDistrictMarker(i.num, i.name, j, i.id);
                    this.jmap.map.addOverlay(g);
                    this.mapDistrictMarkers[i.id] = g
                } else {
                    var g = this.mapDistrictMarkers[i.id];
                    var f = i.num + "套";
                    var h = g.getOptions();
                    if (h.title2 != f) {
                        h.settitle2(f)
                    }
                }
            };
            $.ajax({
                type: "get", url: a, dataType: "json", success: function (f) {
                    for (var g in e.mapDistrictMarkers) {
                        if (f[g] == undefined || f[g] == null) {
                            e.mapDistrictMarkers[g].remove();
                            delete e.mapDistrictMarkers[g]
                        }
                    }
                    for (var g in f) {
                        b.call(e, f[g])
                    }
                }
            });
            return
        }
    }, getJsonMarkers: function () {
        var a = this.jmap.map.getZoom();
        if (a < 10 || a > 12) {
            this.buildUrl(0, 1, 1)
        } else {
            this.buildUrl(0, 1, 2)
        }
    }, buildKWMarkers: function () {
        if (this.search && this.search.len > 0) {
            if (this.kwMarkers[this.keyword] == null || this.kwMarkers[this.keyword] == undefined) {
                var a = this.buildFlagMarker(this.keyword, {lat: this.search.lat, lng: this.search.lng});
                this.jmap.map.addOverlay(a);
                this.kwMarkers[this.keyword] = a
            }
            for (var b in this.kwMarkers) {
                if (b != this.keyword) {
                    this.kwMarkers[b].remove();
                    delete this.kwMarkers[b]
                }
            }
        } else {
            this.removeKWMarkers()
        }
    }, showBuildingMarkers: function (e) {
        var f = this.jmap.map.getZoom();
        this.removeDistrictMarkers();
        for (var c in this.mapMarkers) {
            if (e[c] == undefined || e[c] == null) {
                this.mapMarkers[c].remove();
                delete this.mapMarkers[c]
            }
        }
        var d = 0;
        var g = this;
        this.drawMarkerHandler = [];
        for (var c in e) {
            if (this.mapMarkers[c] != null && this.mapMarkers[c] != undefined) {
                var b = this.mapMarkers[c];
                if (c == this.kwbuildingId) {
                    this.buildingMarkerSwitch(b)
                } else {
                    this.builingMarkerSwitch2(b);
                    if (f >= 16) {
                        b.showTitle2();
                        b.getOptions().onmouseover = function () {
                            var i = g.lastMarker;
                            if (i != null) {
                                var h = i.getOptions();
                                if (h.type == 1 && h.id != g.kwbuildingId) {
                                    i.switchType(0);
                                    h.onmouseover = this.getOptions().onmouseover;
                                    h.onmouseout = this.getOptions().onmouseout
                                }
                            }
                            this.switchType(1)
                        };
                        b.getOptions().onmouseout = function () {
                            this.switchType(0)
                        }
                    } else {
                        b.hideTitle2()
                    }
                }
                this.checkBuildingMarkerNum(b, e[c].num + "套");
                continue
            }
            var a = function (h) {
                return function () {
                    var i = e[h];
                    (function () {
                        var k = this.jmap.Point(i.blat, i.blng);
                        var j = this.buildMarker(i.num, i.name, k, i.id);
                        this.jmap.map.addOverlay(j);
                        if (i.id == this.kwbuildingId) {
                            g.buildingMarkerSwitch(j)
                        } else {
                            if (f >= 16) {
                                j.showTitle2();
                                j.getOptions().onmouseover = function () {
                                    var m = g.lastMarker;
                                    if (m != null) {
                                        var l = m.getOptions();
                                        if (l.type == 1 && l.id != g.kwbuildingId) {
                                            m.switchType(0);
                                            l.onmouseover = this.getOptions().onmouseover;
                                            l.onmouseout = this.getOptions().onmouseout
                                        }
                                    }
                                    this.switchType(1)
                                };
                                j.getOptions().onmouseout = function () {
                                    this.switchType(0)
                                }
                            } else {
                                j.hideTitle2()
                            }
                        }
                        this.mapMarkers[h] = j
                    }).call(g)
                }
            };
            this.drawMarkerHandler.push(window.setTimeout(a(c), d++ * 30))
        }
    }, checkBuildingMarkerNum: function (b, a) {
        var c = b.getOptions();
        if (c.title != a) {
            c.settitle(a)
        }
    }, buildingMarkerSwitch: function (a) {
        a.switchType(1);
        a.showTitle2();
        var b = a.getOptions();
        b.onmouseover = function () {
        };
        b.onmouseout = function () {
        }
    }, builingMarkerSwitch2: function (a) {
        a.switchType(0);
        a.hideTitle2();
        var b = a.getOptions();
        var c = this;
        b.onmouseover = function () {
            c.buildingMarkerMouseOver(this)
        };
        b.onmouseout = function () {
            c.buildingMarkerMouseOut(this)
        }
    }, buildFlagMarker: function (b, d) {
        var c = this;
        var a = new EstateMarker({
            center: d, type: 1, title: b, title2: "", istitle2: false, id: 0, onclick: function () {
            }, onmouseover: function () {
            }, onmouseout: function () {
            }
        });
        return a
    }, buildingMarkerClick: function (a) {
        this.buildingMarkerSwitch(a);
        this.currentMarker = a;
        var b = a.getOptions();
        this.buildingId = b.id;
        this.buildingFilter = {};
        for (var c in this.filter) {
            this.buildingFilter[c] = this.filter[c]
        }
        this.buildingFilter.log = 1;
        this.buildingFilter.cs = "open_infowindow";
        this.infowin_leftcond.find(".lesel a").removeClass("cur");
        this.infowin_sortspan.removeClass("hccur");
        this.infowin_sortlink.removeClass("down");
        this.infowin_sortlink.addClass("up");
        this.infowin_sortlink.attr("value", 0);
        this.selhq.get()[0].checked = false;
        $('.lesel-top a[value="' + this.buildingFilter.p8 + '"]').addClass("cur");
        $('.lesel-bottom a[name="info_p9"]').filter('[value="' + this.buildingFilter.p9 + '"]').addClass("cur");
        $('.lesel-bottom a[name="info_p11"]').filter('[value="' + this.buildingFilter.p11 + '"]').addClass("cur");
        this.showPropWindow(this.buildingId, 1)
    }, buildingMarkerMouseOver: function (a) {
        var c = this.lastMarker;
        if (c != null) {
            var b = c.getOptions();
            if (b.type == 1 && b.id != this.kwbuildingId) {
                c.switchType(0);
                c.hideTitle2();
                b.onmouseover = a.getOptions().onmouseover;
                b.onmouseout = a.getOptions().onmouseout
            }
        }
        a.switchType(1);
        a.showTitle2()
    }, buildingMarkerMouseOut: function (a) {
        a.switchType(0);
        a.hideTitle2()
    }, buildMarker: function (c, b, f, e) {
        var d = this;
        var a = new EstateMarker({
            center: f,
            type: 0,
            title: c + "套",
            title2: b,
            istitle2: false,
            id: e,
            onclick: function () {
                d.buildingMarkerClick(this)
            },
            onmouseover: function () {
                d.buildingMarkerMouseOver(this)
            },
            onmouseout: function () {
                d.buildingMarkerMouseOut(this)
            }
        });
        return a
    }, buildDistrictMarker: function (c, b, f, e) {
        var d = this;
        var a = new EstateMarker({
            center: f, type: 2, title: b, title2: c + "套", id: e, onclick: function () {
                var g = this.getOptions();
                d.jmap.setZoom(14);
                d.jmap.map.setCenter(d.jmap.Point(g.center.lat, g.center.lng))
            }, onmouseover: function () {
                this.switchType(3)
            }, onmouseout: function () {
                this.switchType(2)
            }
        });
        return a
    }, drawPropWindow: function (d) {
        if (d && d != null) {
            $(".lesel-top li").hide();
            $(".lesel-bottom li").hide();
            $('.lesel-top a[value="0"]').parent().show();
            $('.lesel-bottom a[value="0"]').parent().show();
            for (var i in d.areaFacet) {
                $('.lesel-top a[value="' + d.areaFacet[i] + '"]').parent().show()
            }
            for (var i in d.priceFacet) {
                $('.lesel-bottom a[value="' + d.priceFacet[i] + '"]').parent().show()
            }
            this.infowin_officenum.text(d.num_found);
            this.infowin_houselist_ul.empty();
            this.infowin_page.empty();
            this.infowin_title.text(d.building.name).wrap('<a target="blank" href="/loupan/' + d.building.id + '/">');
            if (this.housetype == 1 && d.building.price) {
                this.infowin_builing_price.text(parseFloat(d.building.price));
                this.infowin_builing_price_box.show()
            } else {
                this.infowin_builing_price_box.hide()
            }
            this.loupanLink.attr("href", "/loupan/" + d.building.id);
            if (d.num_pages > 0) {
                this.infowin_page.append($('<span class="pages"></span>').text(d.current_p + "/" + d.num_pages));
                this.infowin_page.show();
                this.infowin_houselist_ul.show();
                this.infowin_emptyinfo_box.hide()
            } else {
                this.infowin_page.hide();
                this.infowin_houselist_ul.hide();
                this.infowin_emptyinfo_box.show()
            }
            var g = this;
            if (d.num_pages > d.current_p) {
                ($('<a href="#" class="next"><em class="left"></em><span>下一页</span><em class="right"></em></a>')).appendTo(g.infowin_page).click(function () {
                    g.buildingFilter.log = 1;
                    g.buildingFilter.cs = "next_page";
                    g.showPropWindow(g.buildingId, d.current_p + 1);
                    return false
                })
            }
            if (d.current_p > 1) {
                ($('<a href="#" class="pre"><em class="left"></em><span>上一页</span><em class="right"></em></a>')).prependTo(g.infowin_page).click(function () {
                    g.buildingFilter.log = 1;
                    g.buildingFilter.cs = "prev_page";
                    g.showPropWindow(g.buildingId, d.current_p - 1);
                    return false
                })
            }
            for (var f in d.DIndex) {
                var b = d.DIndex[f];
                var j = $("<li></li>");
                var a = "";
                if (d.DImages[b]) {
                    a = d.DImages[b]
                }
                var c = $('<a target="_blank" data-sign="true"></a>').attr("href", d.DProps[b].url).attr("rel", d.DProps[b].url).attr("title", d.DProps[b].alt);
                var e = c.clone();
                j.append($('<div class="img"></div>').append(c.append($("<img/>").attr("src", a).attr("alt", d.DProps[b].alt))));
                var h = d.DProps[b].is_quality == 1 ? '<em class="eimg">多图</em>' : "";
                j.append($('<div class="center"><h3>' + h + "</h3></div>"));
                e.text(d.DProps[b].title).prependTo(j.find("h3"));
                $("<div></div>").append($('<span class="sarea"></span>').text(parseInt(d.DProps[b].area) + "平米")).append($("<span></span>").text(d.DProps[b].floor)).append($('<span class="miprice"></span>').text(d.DProps[b].price)).insertAfter(j.find("h3"));
                j.append($('<div class="price"></div>').text(d.DProps[b].totalPrice));
                this.infowin_houselist_ul.append(j)
            }
            this.map_mask.css({display: "block", opacity: "0.5"});
            this.map_mask.parent().css("height");
            this.infowin_houselist_box.insertAfter(this.map_box).show()
        }
    }, hidePropWindow: function () {
        this.lastMarker = this.currentMarker;
        this.infowin_houselist_box.hide();
        this.map_fruitless_box.hide();
        this.map_fruitless_box2.hide();
        this.map_mask.css({display: "none"})
    }, showPropWindow: function (a, c) {
        var b = "/map/search/?" + this.buildUrl(a, c, 3, this.buildingFilter);
        var d = this;
        $.ajax({
            type: "get", url: b, dataType: "json", success: function (e) {
                d.drawPropWindow(e)
            }
        })
    }
};
var JMAP = {};
JMAP.URL = (function () {
    var a = {log: 0, cs: 0};

    function b(g, f) {
        var e = {};
        for (var d in g) {
            e[d] = g[d]
        }
        if (!f) {
            return e
        }
        for (var c in f) {
            e[c] = f[c]
        }
        return e
    }

    return {
        getUrl: function (g, f) {
            var e = b(a, g), c = [];
            if (f.length) {
                for (var d = 0; d < f.length; d++) {
                    (e.hasOwnProperty(f[d]) && delete e[f[d]])
                }
            }
            for (var d in e) {
                c.push(d + "=" + e[d])
            }
            return c.join("&")
        }, setUrl: function (g, f) {
            var e = a, c = [];
            if (f && f.length) {
                for (var d = 0; d < f.length; d++) {
                    (e.hasOwnProperty(f[d]) && delete e[f[d]])
                }
            }
            for (var d in g) {
                e[d] = g[d]
            }
            for (var d in e) {
                c.push(d + "=" + e[d])
            }
            return c.join("&")
        }, getparam: function () {
            return a
        }, update: function () {
            var e = window.location.hash.replace(/#/im, ""), d, f;
            if (e.length == 0) {
                return false
            }
            d = e.split("&");
            for (var c = 0; c < d.length; c++) {
                f = d[c].split("=");
                if (f.length == 2) {
                    a[f[0]] = f[1]
                }
            }
        }
    }
})();
void function (d, b) {
    var a = d.location;

    function c(e) {
        var f = arguments.callee;
        if (!f.urlchange.instance || !f.urlchange.instance instanceof f.urlchange) {
            f.urlchange.instance = new f.urlchange();
            setInterval(function () {
                f.urlchange.instance.ischange()
            }, 100)
        }
        f.urlchange.instance.addfun(e);
        return f.urlchange.instance
    }

    c.urlchange = function () {
        var e = arguments.callee;
        e.instance = this;
        this.funlist = [];
        this.lasturl = a.href
    };
    c.urlchange.prototype = {
        addfun: function (e) {
            if (typeof e == "function") {
                this.funlist.push(e)
            }
        }, runfuns: function () {
            for (var e = 0; e < this.funlist.length; e++) {
                typeof this.funlist[e] === "function" && this.funlist[e](e, this.funlist.length)
            }
        }, ischange: function () {
            if (a.href != this.lasturl) {
                this.runfuns()
            }
            this.lasturl = a.href
        }, length: function () {
            return this.funlist.length
        }
    };
    b.bindurlchange = c
}(window, JMAP);
void function (JMAP) {
    JMAP.eval = function (string) {
        return (new Function("return " + string))()
    }
}(JMAP);
void function (a) {
    function b(d, e, c) {
        this.name = $(d);
        this.key = e;
        this.name.bind("click", function () {
            typeof c === "function" && c.apply(this, [])
        })
    }

    b.prototype = {
        select: function (d) {
            this.name.removeClass("cur");
            var c = this;
            this.name.each(function () {
                var e = $(this);
                if (e.attr("value") == "{" + c.key + ":" + d + "}") {
                    e.addClass("cur")
                }
            })
        }
    };
    a.condition = b
}(JMAP);
JMAP.MAIN = function () {
    var jthis = this;
    var location = window.location, onclick = function () {
        var qthis = $(this), value = JMAP.eval(qthis.attr("value")), novalue = JMAP.eval(qthis.attr("novalue"));
        value.log = 1;
        value.cs = this.name;
        url = JMAP.URL.setUrl(value, novalue);
        location.hash = url
    };
    var cond = {};
    cond.p8 = new JMAP.condition("[name=p8]", "p8", onclick), cond.p9 = new JMAP.condition("[name=p9]", "p9", onclick), cond.p10 = new JMAP.condition("[name=p10]", "p10", onclick);
    cond.p11 = new JMAP.condition("[name=p11]", "p11", onclick);
    $("#houtype").Oselect({CLASSNAME: "houtype", ISMOUSESHOW: false});
    this.initialBlockSelect = function () {
        if (this.region != undefined) {
            $(this.region.getThisObj()).remove()
        }
        this.region = $("#region").Oselect({
            CLASSNAME: "region",
            DEFAULTCLASSNAME: "area-select",
            LIEMPTYTITLE: "板块",
            ONCHANGE: function () {
                var mapsearch = window.mapsearch;
                if (mapsearch != undefined) {
                    var centerInfo;
                    var value = $(this).attr("v");
                    if (value == 0) {
                        var districtInfo = jthis.area.getValue();
                        centerInfo = eval("(" + districtInfo + ")")
                    } else {
                        centerInfo = eval("(" + value + ")")
                    }
                    if (centerInfo != null) {
                        var lat = parseFloat(centerInfo.lat);
                        var lng = parseFloat(centerInfo.lng);
                        var zoom = parseFloat(centerInfo.zoom);
                        if (lat && lng && zoom) {
                            mapsearch.mapCenter.call(mapsearch, lat, lng, zoom)
                        }
                    }
                }
            }
        });
        this.region.clear();
        this.region.additem({title: "请先选择区域 ", value: 0}, false);
        $(this.region.getitem(0)).unbind("click");
        this.region.selectitem(-1)
    };
    this.initialBlockSelect();
    this.initialDistrictSelect = function () {
        if (this.area != undefined) {
            $(this.area.getThisObj()).remove()
        }
        this.area = $("#area").Oselect({
            CLASSNAME: "area",
            DEFAULTCLASSNAME: "area-select",
            LIEMPTYTITLE: "区域",
            ONCHANGE: function () {
                var mapsearch = window.mapsearch;
                if (mapsearch != undefined) {
                    var districtInfo = eval("(" + $(this).attr("v") + ")");
                    url = "/ajax/district/get_blocks?districtid=" + districtInfo.id;
                    $.ajax({
                        type: "get", url: url, success: function (data) {
                            var json = eval("(" + data + ")");
                            if (json.status == "ok") {
                                var block = jthis.region;
                                block.clear();
                                block.additem({title: "不限", value: 0}, false);
                                for (var i in json.data) {
                                    var value = '{"id":"' + json.data[i].id + '","zoom":"' + json.data[i].zoom + '","lat":"' + json.data[i].lat + '","lng":"' + json.data[i].lng + '","pinyin":"' + json.data[i].pinyin + '"}';
                                    block.additem({title: json.data[i].name, value: value}, false)
                                }
                                block.selectitem(-1)
                            }
                        }
                    });
                    var lat = parseFloat(districtInfo.lat);
                    var lng = parseFloat(districtInfo.lng);
                    var zoom = parseFloat(districtInfo.zoom);
                    if (lat && lng && zoom) {
                        mapsearch.mapCenter.call(mapsearch, lat, lng, zoom)
                    }
                }
            }
        });
        this.area.selectitem(-1)
    };
    this.initialDistrictSelect();
    function condchange() {
        JMAP.URL.update();
        var param = JMAP.URL.getparam();
        for (var i in param) {
            if (cond[i]) {
                cond[i].select(param[i])
            }
        }
    }

    condchange();
    JMAP.bindurlchange(condchange)
};
$(function () {
    JMAP.MAIN.call(JMAP)
});