J.ready(function () {
    function a(i, e, g) {
        var f = J.g(i), b = J.g(e), g = g, j, c;
        f.on("mouseover", function (k) {
            h(k)
        });
        f.on("mouseout", function () {
            d()
        });
        b.on("mouseover", function (k) {
            h(k)
        });
        b.on("mouseout", function () {
            d()
        });
        function h(k) {
            k.stop();
            window.clearTimeout(j);
            b.show()
        }

        function d() {
            window.clearTimeout(j);
            j = window.setTimeout(function () {
                b.hide()
            }, g)
        }
    }

    J.globalCitySelector = {};
    J.globalCitySelector = a
});
(function () {
    var b = "_soj", i = "_spd", c = "system-link-track", g = J.s("." + c);

    function d(j) {
        if (j.tagName === "A") {
            if (j.getAttribute("href") && (j.getAttribute(b) || j.getAttribute(i))) {
                return true
            }
        }
    }

    function h(p) {
        var k = p.href, l = k.split("?"), r = l.length, n = (r > 1) ? l[1] : k, s = n.split("#"), m = (r > 1) ? "&" : "?", j = q(p), o = (j) ? m + j : "";

        function q(w) {
            var u = w.href, z = w.getAttribute(b), v = w.getAttribute(i), t = /[?&]from=/, y = /[?&]spread=/, x = [];
            if (!t.test(u) && z) {
                x.push("from=" + encodeURIComponent(z))
            }
            if (!y.test(u) && v) {
                x.push("spread=" + encodeURIComponent(v))
            }
            return x.join("&")
        }

        if (r > 1 && s.length > 1) {
            p.href = l[0] + "?" + s[0] + o + "#" + s[1]
        } else {
            p.href += o
        }
    }

    function e(j) {
        j.setAttribute(b, "");
        j.setAttribute(i, "")
    }

    function f(j) {
        if (d(j)) {
            h(j);
            e(j)
        }
    }

    J.s("body").on("click", function (k) {
        var j = k.srcElement ? k.srcElement : k.target;
        f(j)
    });
    function a(l) {
        var k = l.currentTarget, j = J.s("a", k), m = new RegExp(c);
        if (!m.test(k.className)) {
            return
        }
        if (j.length) {
            J.each(j, function (n, o) {
                f(o[0])
            })
        }
        k.className = k.className.replace(c, "")
    }

    J.each(g, function (j, k) {
        k.on("click", a)
    })
})();
J.ready(function () {
    function b() {
        a()
    }

    b();
    function a() {
        J.s(".li_unselected").each(function (f, d) {
            var g = d.down(), e = d.hasClass("li_itemsnew") ? d.down(1) : null;
            d.on("mouseenter", function () {
                d.addClass("li_hover");
                g.addClass("a_nav_hover");
                e && e.show()
            });
            d.on("mouseleave", function () {
                d.removeClass("li_hover");
                g.removeClass("a_nav_hover");
                e && e.hide()
            })
        });
        var c = J.s(".arrow_upnew");
        c.length && c.each(function (e, d) {
            var f = d.up().up(), g = 0;
            f && (g = (f.width() - 14) / 2);
            d.get().style.left = g + "px"
        })
    }
});
(function () {
    J.g("header").s(".li_selected").each(function (b, a) {
        var c = a.s(".sec_divnew").length && a.s(".sec_divnew").eq(0);
        a.on("mouseenter", function (d) {
            c && c.show();
            d.stop()
        }).on("mouseleave", function (d) {
            c && c.hide();
            d.stop()
        })
    })
})();
(function (a) {
    a.fn.CitySelector = function (f, c) {
        var b = this;
        var e = a("#" + f);
        var d;
        $(b).hover(function () {
            window.clearTimeout(d);
            e.show();
            a(".ac_results").hide()
        }, function () {
            window.clearTimeout(d);
            d = window.setTimeout(function () {
                e.hide()
            }, 300)
        })
    }
})(jQuery);
jQuery(function () {
    jQuery("#switch-city").CitySelector("city_float", 1000)
});
void function () {
    var c = document.getElementById("mybroker"), f, d;
    if (c) {
        f = document.getElementById("mybroker-menu");
        d = document.getElementById("mybroker-over-patch");
        c.onmouseover = function () {
            f.style.display = "inline";
            d.style.display = "inline";
            c.className = "mybroker mybroker-over"
        };
        c.onmouseout = function () {
            f.style.display = "";
            d.style.display = "";
            c.className = "mybroker"
        }
    }
    var e = document.getElementById("header"), b;
    if (e) {
        b = e.getElementsByTagName("a");
        if (b && b.length > 0) {
            for (var a in b) {
                b[a].onfocus = function () {
                    this.blur()
                }
            }
        }
    }
}();
var picLoop = {
    init: function () {
        var a = this;
        $(".toggle_list li").bind("mouseenter", function () {
            a.enter($(this))
        });
        this.timeout = setTimeout(function () {
            a.loopNext()
        }, 5000)
    }, enter: function (a) {
        var b = this;
        $(".toggle_list li").removeClass("toggle_list_on");
        a.addClass("toggle_list_on");
        $("#toggle_banner a").hide();
        $("#toggle_banner a").eq(a.prevAll().length).show();
        clearTimeout(this.timeout);
        this.timeout = setTimeout(function () {
            b.loopNext()
        }, 5000)
    }, loopNext: function () {
        if ($(".toggle_list li.toggle_list_on").next().length > 0) {
            $(".toggle_list li.toggle_list_on").next().mouseenter()
        } else {
            $(".toggle_list li").first().mouseenter()
        }
    }
};
$(function () {
    picLoop.init();
    var b = $(document), a = $(window);
    if ($.browser.msie && $.browser.version == "6.0") {
        $(function () {
            var h = $(".gotop"), d = $("#content"), f = $("#footer"), g = new Date(), e = function () {
                var k = document.documentElement.scrollTop, l = d.offset(), j = a.height(), i = h.height();
                var m = k - 140 + j - i - 80;
                h.css({top: m + "px"});
                h.show()
            };
            h.css({position: "absolute", right: "-28px"});
            a.scroll(e);
            a.resize(e);
            a.scroll()
        })
    }
    var c = function () {
        var d = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop, e = $(".gotop");
        if (d > 0) {
            e.fadeIn("fast")
        } else {
            e.fadeOut("fast")
        }
    };
    a.scroll(c).resize(c)
});