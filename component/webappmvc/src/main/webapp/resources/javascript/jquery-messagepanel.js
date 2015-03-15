/*!
 * jQuery Message plugin
 * http://samenea.com
 *
 * Author: Soroosh Sarabadani
 */
(function($) {

	var messageTempale = '<div style="direction:rtl;" class="@{msgClass}"><span style="float:right;"><img src="@{iconAddress}" alt="@{iconAlt}" /></span><span>@{message}</span></div>';
	var messageCount = 0;

	$.fn.messagePanel = function(options,callback) {
		var defaults = {
			messageSize : 5,
			headerCaption:'System Messages',
			closeImgAddress : '',
			minimizeImgAddress : '',
			maximizeImgAddress : '',
			infoIconAddress : '',
			errorIconAddress : '',
			warningIconAddress : '',
			errorMessageTemplate : '<div  style="direction:rtl;" class="@{msgKey} message @{msgClass}"><span class="error-img-span" ><img src="@{iconAddress}" alt="@{iconAlt}" /></span><span class="error-message-span">@{message}</span></div>',
			infoMessageTemplate : '<div  style="direction:rtl;" class="@{msgKey} message @{msgClass}"><span class="info-img-span"><img src="@{iconAddress}" alt="@{iconAlt}" /></span><span class="info-message-span">@{message}</span></div>',
			warningMessageTemplate : '<div  style="direction:rtl;" class="@{msgKey} message @{msgClass}"><span class="warning-img-span"><img src="@{iconAddress}" alt="@{iconAlt}" /></span><span class="warning-message-span">@{message}</span></div>'
		};
		var options = $.extend(defaults, options);

		// *****************
		var element = this;
		element.append('<div id="message-container"> '
						+ '<span id="header"> '
						+ '<div style="height:20px; vertical-align: middle; background-color:#E3DFDA;"> '
						+ '<p><img class="img-icon-close" id="close" alt="close" src="'
						+ options.closeImgAddress
						+ '" /> '
						+ '<img  class="img-icon-minimize-maximize" id="minimize-maximaze" mode="min"  alt="minimize" src="'
						+ options.minimizeImgAddress + '" />' + options.headerCaption +'</p></div> '
						+ '</span> ' + '<div id="body"></div> ' + '</div> ');
		_closePanel(callback);
		_getClose().click(function() {
			_getContainer().fadeOut();
		});

		_getMinMaxImg().click(function() {
			var mode = _getMinMaxImg().attr('mode');
			if (mode == "max") {
				_closePanel();
			} else {
				_openPanel();
			}
		});

		$.fn.messagePanel.addErrorMessage = function(message,key) {
			
			var errorTemplate = options.errorMessageTemplate;
			errorTemplate = errorTemplate.replace("@{msgClass}", "error");
			errorTemplate = errorTemplate.replace("@{iconAddress}",
					options.errorIconAddress);
			errorTemplate = errorTemplate.replace("@{iconAlt}", "error");
			errorTemplate = errorTemplate.replace("@{message}", message);
			_appendMessage(errorTemplate,key);
		};
		$.fn.messagePanel.addWarningMessage = function(message,key) {
			var warningTemplate = options.warningMessageTemplate;
			warningTemplate = warningTemplate.replace("@{msgClass}", "warning");
			warningTemplate = warningTemplate.replace("@{iconAddress}",
					options.warningIconAddress);
			warningTemplate = warningTemplate.replace("@{iconAlt}", "warning");
			warningTemplate = warningTemplate.replace("@{message}", message);
			_appendMessage(warningTemplate,key);
		};
		$.fn.messagePanel.addInfoMessage = function(message,key) {
			var infoTemplate = options.infoMessageTemplate;
			infoTemplate = infoTemplate.replace("@{msgClass}", "info");
			infoTemplate = infoTemplate.replace("@{iconAddress}",
					options.infoIconAddress);
			infoTemplate = infoTemplate.replace("@{iconAlt}", "info");
			infoTemplate = infoTemplate.replace("@{message}", message);
			_appendMessage(infoTemplate,key);
		};
		$.fn.messagePanel.clearMessages = function(key) {
			if(key){
				var _keys = _getBody().find("."+key);
				messageCount -=  _keys.size();
				_keys.remove();
			}
			else{
				messageCount=0;
			_getBody().empty();
			}
		};
		$.fn.messagePanel.open = function(){
			_openPanel();
		};
		$.fn.messagePanel.close = function(){
			_closePanel();
		};

		// private functions
		function _appendMessage(template,key) {
				if (key){						 
					template = template.replace("@{msgKey}",key);
				}
				else{
					template = template.replace("@{msgKey}","");
				}
				
				_openPanel();
				_getBody().prepend(template);
				_getBody().find('.message').first().fadeOut();
				_getBody().find('.message').first().fadeIn();
				messageCount++;
				if(messageCount > options.messageSize){
					_getBody().find('.message').last().remove();
				}
				
		}

		function _getContainer() {
			return element.find('#message-container');
		}
		function _getHeader() {
			return element.find('#header')
		}

		function _getBody() {
			return element.find('#body');
		}

		function _getMinMaxImg() {
			return element.find('#minimize-maximaze');
		}
		function _getHeader() {
			return element.find('#head');
		}

		function _getClose() {
			return element.find('#close');
		}
		
		function _openPanel(){
			_getMinMaxImg().attr('src', options.minimizeImgAddress);
			_getMinMaxImg().attr('alt', 'minimize');
			_getMinMaxImg().attr('mode', 'max');
			_getContainer().css("height", "");
			_getContainer().css("bottom", "");
		}
		
		function _closePanel(callback){
			_getMinMaxImg().attr('src', options.maximizeImgAddress);
			_getMinMaxImg().attr('alt', 'maximize');
			_getMinMaxImg().attr('mode', 'min');
			_getContainer().animate({
				height : '12px',
				bottom : 5
			}, 500,callback);
		}
	};

})(jQuery);