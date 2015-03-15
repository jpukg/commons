var UpdateModel = {
	APPEND : 'APPEND',
	UPDATE : 'UPDATE'
};

var SEA = new (function() {
	loadingAddress = baseImgAddress + "/loading.gif";
	loadingImgId = "a1b2c3d4";
	this.update = function(url, dependVariable, triggerSource, event,
			container, updateModel, loadingContainer) {
		$(triggerSource).ready(function() {
			$(triggerSource).on(event, function() {
				manageContainer($(container), updateModel);
				showLoading(container, loadingContainer, updateModel);
				
				$.get(url + eval(dependVariable), function(data) {
					disapearLoading(loadingContainer);
					$(container).append(data);

				});
			});
		});
	};

	function manageContainer(obj, updateModel) {
		if (updateModel == UpdateModel.UPDATE) {
			obj.empty();
		} else if (updateModel == UpdateModel.APPEND) {
		}
	}
	

	function showLoading(container, loadingContainer, updateModel) {
		loadingImgTag = "<div  id='" + loadingImgId + "'><img src='"
				+ loadingAddress + "'" + "/></div>";
		if (!loadingContainer) {
			$(container).append(loadingImgTag);
		} else {

			$(loadingContainer).append(loadingImgTag);
			$(loadingContainer).show();
		}
	}
	

	function disapearLoading(loadingContainer) {
		if (loadingContainer) {
			$(loadingContainer).empty();
		} else {
			$("#" + loadingImgId).empty();
		}
	}
	
})();
