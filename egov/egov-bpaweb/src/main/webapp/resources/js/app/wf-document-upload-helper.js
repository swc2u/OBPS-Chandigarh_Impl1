$(document).ready(function(){	
	initializeWFTooltips();	
	$('.wf-files-upload-container').each(function(idx) {		
		var existingFilesLen=$(this).find('.wf-files-viewer a[data-id]').length;
		var availableFilesLen=$(this).find('input:file').length;		
		if(existingFilesLen === availableFilesLen && $(this).find('.wf-file-add[data-unlimited-files="true"]').length == 0) {
			$(this).find('.wf-file-add').hide();
		}
		
	});
	
});

function getNewWFFileViewer(fileName) {
	return $('<div class="wf-file-viewer" title="'+ fileName +'" data-toggle="tooltip"><a class="delete" href="javascript:void(0);"></a><span class="doc-numbering"></span></div>');
}

$(document).on('change','.wf-files-upload-container input:file',function(e) {	
	var allowedExtenstion=$(this).closest('.wf-files-upload-container').data('allowed-extenstion');
	var maxFileSize = $(this).closest('.wf-files-upload-container').data('file-max-size');	
	$(this).parent().find('.error').remove();	
	$filesViewerContainer=$(this).parent().find('.wf-files-viewer');
	$addFileBtn=$filesViewerContainer.find('.wf-file-add');
	$fileInput=$(this);	
	input=e.target;	
	if (input.files && input.files[0]) {
		var reader = new FileReader();
		var fileName = input.files[0].name;
		var extension = fileName.split('.').pop().toLowerCase();		
		var isMaxLimitReached=false;		
		if(maxFileSize){
			isMaxLimitReached = parseInt(maxFileSize)*1024*1000 < input.files[0].size;
		}		
		if(allowedExtenstion && allowedExtenstion.toUpperCase().split(',').indexOf(extension.toUpperCase()) < 0){
			bootbox.alert($('#uploadMsg').val() +allowedExtenstion.toUpperCase());
			$(this).val('');
			return;
		}
		else if(isMaxLimitReached){
			bootbox.alert($('#fileSizeLimit').val()+maxFileSize+' MB!');
			$(this).val('');
			return;
		}
		
        reader.onload = function (e) {
        	$fileViewer=getNewWFFileViewer(fileName);
        	$addFileBtn.before($fileViewer);
        	if(['jpg', 'jpeg', 'png', 'gif', 'tiff'].indexOf(extension)>=0){
        		$fileViewer.css({
            		'background-image':'url(' + e.target.result + ')',
            		'background-position':'center',
            		'background-size':'cover'
            	});
        	} else {
        		if(['pdf'].indexOf(extension)>=0) {
        			$fileViewer.append('<i class="fa fa-file-pdf-o" aria-hidden="true"></i>');
        		} else if(['txt'].indexOf(extension)>=0) {
        			$fileViewer.append('<i class="fa fa-file-text-o" aria-hidden="true"></i>');
        		} else if(['doc','docx','rtf'].indexOf(extension)>=0) {
        			$fileViewer.append('<i class="fa fa-file-word-o" aria-hidden="true"></i>');
        		} else if(['zip'].indexOf(extension)>=0) {
        			$fileViewer.append('<i class="fa fa-file-archive-o" aria-hidden="true"></i>');
        		} else if(['xls','xlsx'].indexOf(extension)>=0) {
        			$fileViewer.append('<i class="fa fa-file-excel-o" aria-hidden="true"></i>');
        		} else{
        			$fileViewer.append('<i class="fa fa-file-o" aria-hidden="true"></i>');
        		}
        	}
        	
        	$fileViewer.data('fileInput', $fileInput);
        	if($fileInput.data('isLast')){
        		$addFileBtn.hide();
        	}
        	
        	initializeWFTooltips();
        	addWFDocumentNumbering();
        }
        reader.readAsDataURL(input.files[0]);
	}																																																																																																																																																																																																																																																																																																																																																																																																																																																																																														
});

function addWFDocumentNumbering() {
	$(".wf-files-viewer").each(function(){
	  var count=1;
	  $(this).find('.wf-file-viewer').each(function(){
		$(this).find('.doc-numbering').html(count);
		count++;
	  });
	});
}

$(document).on('click','.wf-file-add',function() {
		
	if($(this).data('unlimited-files') && $(this).data('file-input-name')) {
	
		var isFileInputNotAvailable=true;
		$(this).parent().find('input:file').each(function(idx) {
			if(!$(this).val()){
				$(this).trigger('click');
				isFileInputNotAvailable=false;
				return false;
			}
		});
		
		if(isFileInputNotAvailable){
			$file=$('<input type="file" class="hide" name="'+ $(this).data('file-input-name') +'">');
			$(this).closest(".wf-files-upload-container").append($file);
			$file.trigger('click');
		}
	}
	else {
		
		$fileUploadContainer=$(this).closest('.wf-files-upload-container');
		
		var length=$fileUploadContainer.find('input:file').length;
		var existingFiles=$(this).parent().find('a[data-id]').length;
		$fileUploadContainer.find('input:file').each(function(idx){
			if(!$(this).val() && idx>existingFiles-1){
				$(this).data('isLast', idx===length-1);
				$(this).trigger('click');
				return false;
			}
		});
	}
	
});

function addWFDeletedFileId(fileId){
	var deleteFileIds=$('#deletedFilestoreIds').val();
	var deletedIds=[];
	
	if(deleteFileIds)
	 deletedIds=deleteFileIds.split(',');
	
	deletedIds.push(fileId);
	$("#deletedFilestoreIds").val(deletedIds.join());
}

//delete event
$(document).on('click','.wf-file-viewer a.delete',function(){
	if($(this).data('id')){
		var id=$(this).data('id');
		addWFDeletedFileId(id);
	}
	else{
	  $fileopen = $(this).parent().data('fileInput');
	  $fileopen.val('');
	}
	$(this).closest('.wf-files-upload-container').find('.file-add').show();
	removeWFTooltip($(this).parent());
	$(this).parent().remove();
	addWFDocumentNumbering();
});

$(document).on('click','div.wf-file-viewer',function(e){
    if(e.target !== e.currentTarget) return;
    var url = $(this).css("background-image")
    if(url && url!=='none'){
		url=url.replace(/.*\s?url\([\'\"]?/, '').replace(/[\'\"]?\).*/, '');
	    showImage(url);
    }
    else{
      bootbox.alert($('#noPreviewAvailble').val());
    }
    
});

function showImage(url) {
	$('#imgModel').show();
	$('#previewImg').attr('src', url);
}

$(document).on('click','span.closebtn',function(){
	$('#imgModel').hide();
});

function validateUploadWFFilesMandatory(){
	var isValid=true;	
	$('.wf-files-upload-container[required]').each(function(idx){
		if($(this).find('div.wf-file-viewer').length === 0){
			if(isValid){
				isValid=false;
				if (typeof focusToWFTabElement === 'function') {
					focusToWFTabElement(this);
				}
			}
				
			$errorLabel=$('<label class="error">Required</label>');
			$(this).append($errorLabel);
		}
		else{
			$(this).find('.error').remove();
		}
	});
	return isValid;
}

function focusToWFTabElement(element) {
	$('#settingstab a[href="#' + jQuery(element).closest(".tab-pane").attr('id') + '"]').tab('show');
}
	
function initializeWFTooltips(){
	try{
		$('*[data-toggle="tooltip"]').tooltip()
	}
	catch(exception){
		console.log('Error', 'Tooltip js not found');
	}
}

function removeWFTooltip($elem){
	try{
		$elem.tooltip('destroy');
	}
	catch(exception){
		console.log('Error', 'Tooltip js not found');
	}
}