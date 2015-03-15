
$(document).ready(function() {
	hover();
	
	 function hover()
							{
							  $("#mytable tr:even").addClass("eventr");;
						      $("#mytable tr:odd").addClass("eventr");;
						       $("#mytable tr").mouseover(function() {$(this).addClass("trover");}).mouseout(function() {
							   $(this).removeClass("trover");}).click(function() {$(this).toggleClass("trclick");}); 
						   
							}
	
                           $('#btnrun').click(function(){
							window.location.href = "wizard.html";						 
						   });
                     
						 $('#accspan').click(function(){
							  $('#filteracc').fadeIn("slow");
							   $('#filtercu').hide();
						 });
						   
						  $('#cuspan').click(function(){
							  $('#filteracc').hide();
							   $('#filtercu').fadeIn("slow");


						 });
						 $('#spanlogout').click(function(){
							
						 });
					
                    $('.last').first().hide();
					$('#h-wrap').hover(function(){
							$(this).toggleClass('active');
							$("#h-wrap ul").css('display', 'block');
						}, function(){
							$(this).toggleClass('active');
							$("#h-wrap ul").css('display', 'none');
					});
					$(function() {
					 $('#txtbdate').datepicker({ dateFormat: 'yy/mm/dd'});
					  $('#txtcdate').datepicker({ dateFormat: 'yy/mm/dd'});
					});
					
					});
					