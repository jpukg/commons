
(function ($) {
    $.fn.captcha = function (options, callback) {
        var defaults = {
            captchaInputName : 'j_captcha',
            captchaImageSrc:'captcha/image',
            reloadImageSrc:'resources/image/reload.png'
        };
        var options = $.extend(defaults, options);
        var element = this;

        var template=
            '<div class="captchaContainer">' +
                '<div class="captcha">' +
                    '<div>' +
                        '<img class="captchaImage"  src="'+options.captchaImageSrc+'"/>' +
                    '</div>' +
                    '<div>' +
                        '<input class="captchaText" name="'+options.captchaInputName+'" type="text"/>' +
                    '</div>' +
                '</div>' +
                '<div class="reloadImage">' +
                    '<img  src="'+options.reloadImageSrc+'" />' +
                '</div>' +
            '</div>'
        element.append(template);
        element.find("div.reloadImage img").mousedown(function () {
            var captchaImage = element.find("img.captchaImage");
            captchaImage.attr('src', captchaImage.attr('src'));
        });
        callback();
        return element;
    }
})(jQuery);