/*
 * 位置任意对齐
 *
 * @author  Lukin <my@lukin.cn>
 */

jQuery && (function ($) {
	// 取得最大的zIndex
	    $.fn.maxIndex = function(){
	        var max = 0, index;
	        this.each(function(){
	            index = $(this).css('z-index');
	            index = isNaN(parseInt(index)) ? 0 : index;
	            max = Math.max(max, index);
	        });
	        return max;
	    };

    /**
     * 对齐常量
     */
    $.align = {
        TL: 'tl',   // 左上
        TC: 'tc',   // 中上
        TR: 'tr',   // 右上
        CL: 'cl',   // 左中
        CC: 'cc',   // 中对齐
        CR: 'cr',   // 右中
        BL: 'bl',   // 左下
        BC: 'bc',   // 中下
        BR: 'br'    // 右下
    };
    /**
     * 位置对齐
     *
     * @example
     *      $(selector).align(points, offset, parent);
     *      $(selector).center(offset, parent);
     * @param points 对齐方式
     *      第一个字符取值 t,b,c ，第二个字符取值 l,r,c，可以表示 9 种取值范围
     *      分别表示 top,bottom,center 与 left,right,center 的两两组合
     * @param offset
     */
    $.fn.align = function(points, offset, parent) {
        parent = parent || window;
        var self = this, wrap, inner, diff, xy = this.offset();
        if (!$.isArray(points)) {
            points = [points, points];
        }
        offset = offset || [0,0];

        var getOffset = function(node, align) {
            var V = align.charAt(0),
                H = align.charAt(1),
                offset, w, h, x, y;

            if (node) {
                offset = node.offset();
                w = node.outerWidth();
                h = node.outerHeight();
            } else {
                offset = {left:$(parent).scrollLeft(), top:$(parent).scrollTop()};
                w = $(parent).width();
                h = $(parent).height();
            }
            x = offset.left;
            y = offset.top;
            if (V === 'c') {
                y += h / 2;
            } else if (V === 'b') {
                y += h;
            }

            if (H === 'c') {
                x += w / 2;
            } else if (H === 'r') {
                x += w;
            }
            return { left: x, top: y };
        }

        wrap  = getOffset(null, points[0]);
        inner = getOffset(self, points[1]);
        diff  = [inner.left - wrap.left, inner.top - wrap.top];

        xy = {
            left: Math.max(xy.left - diff[0] + (+offset[0]), 0),
            top: Math.max(xy.top - diff[1] + (+offset[1]), 0),
            position: $.inArray(this.css('position'), ['absolute','relative','fixed']) == -1 ? 'absolute' : this.css('position')
        };
        return this.css(xy);
    }
    /**
     * 居中
     */
    $.fn.center = function(offset, parent) {
        return this.align('cc', offset, parent);
    }

})(jQuery);
