$(function(){
	$('#resetExcelUpload').attr('disabled', true);
});
var dropzone,
    dropzonePreviewNode = document.querySelector("#dropzone-preview-list"),
    inputMultipleElements =
        ((dropzonePreviewNode.id = ""),
        dropzonePreviewNode &&
            ((previewTemplate = dropzonePreviewNode.parentNode.innerHTML),
            dropzonePreviewNode.parentNode.removeChild(dropzonePreviewNode),
            (dropzone = new Dropzone(".dropzone", 
            { 
            url: "/excelTest", 
            method: "post", 
            previewTemplate: previewTemplate, 
            previewsContainer: "#dropzone-preview",
            autoProcessQueue: false, // 자동으로 보내기. true : 파일 업로드 되자마자 서버로 요청,
										// false : 서버에는 올라가지 않은 상태. 따로
										// this.processQueue() 호출시 전송
		    clickable: true, // 클릭 가능 여부
		    autoQueue: false, // 드래그 드랍 후 바로 서버로 전송
// createImageThumbnails: true, //파일 업로드 썸네일 생성
		
// thumbnailHeight: 120, // Upload icon size
// thumbnailWidth: 120, // Upload icon size
		
		   maxFiles: 1, // 업로드 파일수
		   maxFilesize: 1000, // 최대업로드용량 : 100MB
		   paramName: 'file', // 서버에서 사용할 formdata 이름 설정 (default는 file)
		   parallelUploads: 1, // 동시파일업로드 수(이걸 지정한 수 만큼 여러파일을 한번에 넘긴다.)
		   uploadMultiple: false, // 다중업로드 기능
		   timeout: 3000000, // 커넥션 타임아웃 설정 -> 데이터가 클 경우 꼭 넉넉히 설정해주자
		
		   addRemoveLinks: true, // 업로드 후 파일 삭제버튼 표시 여부
// dictRemoveFile: '삭제', // 삭제버튼 표시 텍스트
		   acceptedFiles: '.zip', // ZIP 파일 포맷만 허용
		}))),FilePond.registerPlugin(FilePondPluginFileEncode, FilePondPluginFileValidateSize, FilePondPluginImageExifOrientation, FilePondPluginImagePreview),
        document.querySelectorAll("input.filepond-input-multiple"));
		dropzone.on('dragend',function(e){

		});
		dropzone.on('addedfile',function(e){
			$('#resetExcelUpload').attr('disabled', false);
			$('#resetExcelUpload').on('click',function(){
				dropzone.processQueue();
			});
		});
		dropzone.on("drop", function(e) {
			  // do something
			});

		dropzone.on("dragstart", function(e) {
		  // do something
		});

		dropzone.on("dragend", function(e) {
		  // do something
		});

		dropzone.on("dragenter", function(e) {
		  // do something
		});

		dropzone.on("dragover", function(e) {
		  // do something
		});

		dropzone.on("dragleave", function(e) {
		  // do something
		});

		dropzone.on("addedfile", function(file) {
		  // do something
		});

		dropzone.on("processing", function(file) {
		  // do something
		});

		dropzone.on("removedfile", function(file) {
		  // do something
		});

		dropzone.on("thumbnail", function(file, dataURL) {
		  // do something
		});

		dropzone.on("error", function(file) {
		  // do something
		});

		dropzone.on("uploadprogress", function(file, progress, bytesSent) {
		  // do something
		});

		dropzone.on("sending", function(file, formData) {
		  // do something
		});

		dropzone.on("success", function(file) {
		  // do something
		});

		dropzone.on("complete", function(file) {
		  // do something
		});

		dropzone.on("canceled", function(file) {
		  // do something
		});

		dropzone.on("maxfilesreached", function(file) {
		  // do something
		});

		dropzone.on("maxfilesexceeded", function(file) {
		  // do something
		});

		dropzone.on("processingmultiple", function(fileList) {
		  // do something
		});

		dropzone.on("sendingmultiple", function(fileList) {
		  // do something
		});

		dropzone.on("successmultiple", function(fileList) {
		  // do something
		});

		dropzone.on("completemultiple", function(fileList) {
		  // do something
		});

		dropzone.on("canceledmultiple", function(fileList) {
		  // do something
		});

		dropzone.on("reset", function(e) {
		  // do something
		});

		dropzone.on("queuecomplete", function(e) {
		  // do something
		});

		dropzone.on("totaluploadprogress", function(uploadProgress, totalBytes, totalBytesSent) {
		  // do something
		});
var secdropzone,
	secdropzonePreviewNode = document.querySelector("#sec-dropzone-preview-list"),
    inputMultipleElements =
        ((secdropzonePreviewNode.id = ""),
        secdropzonePreviewNode &&
            ((previewTemplate = secdropzonePreviewNode.parentNode.innerHTML),
            secdropzonePreviewNode.parentNode.removeChild(secdropzonePreviewNode),
            (secdropzone = new Dropzone(".sec.dropzone", 
            		{ 
            	url: "/excelTest", 
            	method: "post", 
            	previewTemplate: previewTemplate, 
            	previewsContainer: "#sec-dropzone-preview", 
                autoProcessQueue: false, // 자동으로 보내기. true : 파일 업로드 되자마자 서버로 요청
										// false : 서버에는 올라가지 않은 상태. 따로
										// this.processQueue() 호출시 전송
    		    clickable: true, // 클릭 가능 여부
    		    autoQueue: false, // 드래그 드랍 후 바로 서버로 전송
    // createImageThumbnails: true, //파일 업로드 썸네일 생성
    		
    // thumbnailHeight: 120, // Upload icon size
    // thumbnailWidth: 120, // Upload icon size
    		
    		   maxFiles: 1, // 업로드 파일수
    		   maxFilesize: 1000, // 최대업로드용량 : 100MB
    		   paramName: 'file', // 서버에서 사용할 formdata 이름 설정 (default는 file)
    		   parallelUploads: 1, // 동시파일업로드 수(이걸 지정한 수 만큼 여러파일을 한번에 넘긴다.)
    		   uploadMultiple: false, // 다중업로드 기능
    		   timeout: 3000000, // 커넥션 타임아웃 설정 -> 데이터가 클 경우 꼭 넉넉히 설정해주자
    		
    		   addRemoveLinks: true, // 업로드 후 파일 삭제버튼 표시 여부
    // dictRemoveFile: '삭제', // 삭제버튼 표시 텍스트
    		   acceptedFiles: '.xlsx', // excel 파일 포맷만 허용
            		}))),
        FilePond.registerPlugin(FilePondPluginFileEncode, FilePondPluginFileValidateSize, FilePondPluginImageExifOrientation, FilePondPluginImagePreview),
        document.querySelectorAll("input.filepond-input-multiple"));
		secdropzone.on('dragend',function(e){
			console.log('gd');
		});
		secdropzone.on('addedfile',function(e){
			console.log('add');
		});





















