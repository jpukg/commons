var messagetamplaterequired = 'لطفا یک الگو پیام را انتخاب نمایید';
var datefromrequired = 'لطفا تاریخ را انتخاب نمایید';
var timefromrequired = 'لطفا زمان را انتخاب نمایید';
var jobtitlerequired = 'لطفا عنوان تکلیف را وارد نمایید ';
var jobdescriptionrequired = 'لطفا توضیحات تکلیف را وارد نمایید';
var parameterrequired = 'لطفا تمام فیلد هارا با دقت کامل نمایید';

$(document).ready(function() {

	getsetting();
	initdate();
	$('.wizardcontent').hide();
	$('#wizardcontent').hide();

	$('#schedulercontainer').hide();
	load_state(1);
	$('.wizard-button').click(function() {
		$('#wizardcontent').hide();
		var current_state = $('#wizard').attr('class');
		current_state = parseInt(current_state.replace(/(step_)/, ""));
		if ($(this).attr('id') == 'next') {
			message.messagePanel.clearMessages('step2');
			load_state(++current_state);
			
		} else if ($(this).attr('id') == 'previous') {
			
			load_state(--current_state);
			message.messagePanel.clearMessages('step2');
			message.messagePanel.clearMessages('wizardValidation_step3');
			message.messagePanel.clearMessages('wizardValidation_scheduled');
			
			
			
		}

	});

});

function validationStep2() {
	var state = true;
	message.messagePanel.clearMessages('step2');
	$('.requiredvalidation')
			.filter(
					function(index) {

						if ($(this).val().replace(/\s/g, "") == "") {

							var errorMessage = 'لطفا  فیلد '
									+ $(this).attr('display') + ' '
									+ 'را با دقت کامل نمایید  ';
							message.messagePanel.addErrorMessage(errorMessage,
									'step2');
							state = false;
							
						} 

					});

	return state;

}

function validationStep1() {

	state = true;
	var title = $('#jobTitle').val();
	message.messagePanel.clearMessages('wizardValidation');
	var description = $('#jobDescription').val();
	if (title.replace(/\s/g, "") == "") {
		message.messagePanel.addErrorMessage(jobtitlerequired,
				'wizardValidation');
		state = false;
	}

	if (description.replace(/\s/g, "") == "") {

		message.messagePanel.addErrorMessage(jobdescriptionrequired,
				'wizardValidation');
		state = false;
	}
	return state;

}

function validatePeriodicYearly() {
	var txttimedaily = $('#txttimefrom').val();
	var txtbdate = $('#txtdatefrom').val();
	var txtdateperiodic = $('#txtdateofyear').val();
	var txtdateto = $('#txtdateperiodic').val();
	var txttimeto = $('#txttimedaily').val();
	var txttime=$('#time').val();
	message.messagePanel.clearMessages('wizardValidation_scheduled');
	state = true;
	if (txttimedaily.replace(/\s/g, "") == "") {
		message.messagePanel.addErrorMessage(datefromrequired,
				'wizardValidation_scheduled');
		state = false;
	}
	if (txtdateperiodic.replace(/\s/g, "") == "") {
		message.messagePanel.addErrorMessage(timefromrequired,
				'wizardValidation_scheduled');
		state = false;
	}
	if (txtbdate.replace(/\s/g, "") == "") {
		message.messagePanel.addErrorMessage(datefromrequired,
				'wizardValidation_scheduled');
		state = false;
	}
	if (txtdateto.replace(/\s/g, "") == "") {
		message.messagePanel.addErrorMessage(datefromrequired,
				'wizardValidation_scheduled');

		state = false;
	}
	if (txttimeto.replace(/\s/g, "") == "") {
		message.messagePanel.addErrorMessage(timefromrequired,
				'wizardValidation_scheduled');
		state = false;
	}
	if (txtdateperiodic.replace(/\s/g, "") == "") {
		message.messagePanel.addErrorMessage(datefromrequired,
				'wizardValidation_scheduled');
		state = false;
	}
	if (txttime.replace(/\s/g, "") == "") {

		message.messagePanel.addErrorMessage(parameterrequired,
				'wizardValidation_scheduled');
		state = false;
	}
	return state;
}
function validatePeriodic() {

	var value = $('#periodiclevel').val();
	if (value == 'WEEKLY') {

		return validatePeriodicYearly();

	} else {

		return validatePeriodicTypeTime();
	}

	return true;

}
function validatePeriodicTypeTime() {
	var txttimefrom = $('#txttimefrom').val();
	var txtdatefrom = $('#txtdatefrom').val();
var txttime=$('#time').val();
	var txtdateto = $('#txtdateperiodic').val();
	var txttimeto = $('#txttimedaily').val();

	state = true;
	message.messagePanel.clearMessages('wizardValidation_scheduled');
	if (txttimefrom.replace(/\s/g, "") == "") {
		message.messagePanel.addErrorMessage(datefromrequired,
				'wizardValidation_scheduled');
		state = false;
	}
	if (txtdatefrom.replace(/\s/g, "") == "") {
		message.messagePanel.addErrorMessage(timefromrequired,
				'wizardValidation_scheduled');
		state = false;
	}

	if (txtdateto.replace(/\s/g, "") == "") {
		message.messagePanel.addErrorMessage(datefromrequired,
				'wizardValidation_scheduled');
		state = false;
	}
	if (txttimeto.replace(/\s/g, "") == "") {

		message.messagePanel.addErrorMessage(timefromrequired,
				'wizardValidation_scheduled');
		state = false;
	}
	if (txttime.replace(/\s/g, "") == "") {

		message.messagePanel.addErrorMessage(parameterrequired,
				'wizardValidation_scheduled');
		state = false;
	}
	return state;

}
function initdate() {
	$('#txtbdate').datepicker({
		dateFormat : 'yy/mm/dd'
	});
	$('#txtcdate').datepicker({
		dateFormat : 'yy/mm/dd'
	});
}

function validate() {
	var timebox = $("input[type='radio']:checked").val();
	message.messagePanel.clearMessages('wizardValidation_scheduled');
	switch (timebox) {
	case 'NOW':
		return true;
		break;
	case 'SCHEDULED':
		return validationscheduled();
		break;
	case 'PERIODIC':
		return validatePeriodic();
		break;
	return true;
}

}
function validationscheduled() {

var txtdatefrom = $('#txtdatefrom').val();
var txttimefrom = $('#txttimefrom').val();
state = true;
message.messagePanel.clearMessages('wizardValidation_scheduled');
if (txttimefrom.replace(/\s/g, "") == "") {
	message.messagePanel.addErrorMessage(timefromrequired,
			'wizardValidation_scheduled');
	state = false;

}
if (txtdatefrom.replace(/\s/g, "") == "") {
	message.messagePanel.addErrorMessage(datefromrequired,
			'wizardValidation_scheduled');
	state = false;

}
return state;
}
function getsetting() {
$('.scheduler').click(function() {
	var timebox = $(this).val();
	switch (timebox) {
	case 'NOW':
		$('#m_scheduleddiv').hide();
		$('#m_periodicdiv').hide();

		break;
	case 'SCHEDULED':
		$('#m_scheduleddiv').show();
		$('#m_periodicdiv').hide();

		break;
	case 'PERIODIC':
		$('#m_scheduleddiv').show();
		$('#m_periodicdiv').show();

		break;
	}
	$('.daily').hide();
	$('.weekely').hide();
	$('.yearly').hide();
	$('.monthly').hide();

});
}
function getperiodiclevel() {

$('#periodiclevel').change(function() {
	var value = $(this).val();
	//

	switch (value) {
	case 'MINUTELY':
	case 'DAILY':

		$('.weekly').hide();
		$('#time').show();
		$('#txttime').show();
		break;
	case 'WEEKLY':
		$('.weekly').show();
		$('#time').hide();
		$('#txttime').hide();
		break;
	}

});
}
function activestep(current_state, iterator) {
$('#mainNav li').each(function() {

	var step = $(this);

	if (iterator == current_state) {
		step.attr('class', 'current');
	} else if (current_state - iterator == 1) {
		step.attr('class', 'lastDone');
	} else if (current_state - iterator > 1) {
		step.attr('class', 'done');
	} else {
		step.attr('class', '');
	}

	if (iterator == 5) {
		step.addClass('mainNavNoBg');

	}

	iterator++;
});
}
function load_state(current_state) {
if (current_state > 0 && current_state < 6) {
	// disable all buttons while loading the state

	// set the wizard class to current state for next iteration
	$('#wizard').attr('class', 'step_' + current_state);
	var iterator = 1;
	activestep(current_state, iterator);

	switch (current_state) {
	case 1:
		$('#step_1').fadeIn("slow");
		$('#step_2').hide();
		$('#step_3').hide();
		$('#step_4').hide();
		$('#step_5').hide();
		$('#next').attr("class", "button wizard-button");
		$('#next').removeAttr("disabled");
		$('#previous').attr("class", "disablebutton wizard-button");
		break;
	case 2:
		if (validationStep1() == true) {
			$('#step_2').fadeIn("slow");
			$('#step_1').hide();
			$('#step_3').hide();
			$('#step_4').hide();
			$('#step_5').hide();
			$('#next').attr("class", "button wizard-button");
			$('#next').removeAttr("disabled");
			$('#previous').attr("class", "button wizard-button");
		} else {
			load_state(1);
		}
		break;
	case 3:
		if (validationStep2() == true) {
			$('#step_3').fadeIn("slow");
			$('#step_2').hide();
			$('#step_1').hide();
			$('#step_4').hide();
			$('#step_5').hide();
			$('#next').attr("class", "button wizard-button");
			$('#next').removeAttr("disabled");
			$('#previous').attr("class", "button wizard-button");
		} else {
			load_state(2);
		}
		break;
	case 4:

		if (selectedRow() == true) {

			$('#step_4').fadeIn("slow");
			$('#step_2').hide();
			$('#step_3').hide();
			$('#step_1').hide();
			$('#step_5').hide();
			$('#next').attr("class", "button wizard-button");
			$('#next').removeAttr("disabled");
			$('#previous').attr("class", "button wizard-button");

		} else {

			load_state(3);

		}

		break;
	case 5:

		if (validate() == true) {
			message.messagePanel.clearMessages('wizardValidation_scheduled');
			$('#step_5').fadeIn("slow");
			$('#step_2').hide();
			$('#step_3').hide();
			$('#step_4').hide();
			$('#step_1').hide();
			$('#previous').removeAttr("disabled");
			$('#previous').attr("class", "button wizard-button");
			$('#next').attr("class", "disablebutton wizard-button");

		} else {

			load_state(4);

		}
		break;
	}

}

}
function selectedRow() {
var rowid = $("#messagetemplates").jqGrid('getGridParam', 'selrow');

message.messagePanel.clearMessages('wizardValidation_step3');
if (rowid == null) {
	message.messagePanel.addErrorMessage(messagetamplaterequired,
			'wizardValidation_step3');

	return false;
} else {
	
	return true;
}

}
