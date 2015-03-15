/*!
 * jQuery ASCII Number plugin
 * http://samenea.com
 *
 * Author: Soroosh Sarabadani
 */
(function ($) {

    $.fn.asciiNumber = function () {
        var persianNumbers = ['\u06f0', '\u06f1', '\u06f2', '\u06f3', '\u06f4', '\u06f5', '\u06f6', '\u06f7', '\u06f8', '\u06f9'];
        var arabicNumbers = ['\u0660', '\u0661', '\u0662', '\u0663', '\u0664', '\u0665', '\u0666', '\u0667', '\u0668', '\u0669'];
        var element = this;
        $(element).click(eventDelegate);
        $(element).keyup(eventDelegate);
        $(element).change(eventDelegate);

        function eventDelegate() {
            $(this).val(_refineNumber($(this).val()));
        }

        function _refineNumber(value) {
            var result = value;

            for (var i = 0; i <= 9; i++) {
                for (var j = 0; j < result.length; j++) {
                    result = result.replace(persianNumbers[i], i);
                    result = result.replace(arabicNumbers[i], i);
                }
            }
            return result;

        }

    };
})(jQuery);