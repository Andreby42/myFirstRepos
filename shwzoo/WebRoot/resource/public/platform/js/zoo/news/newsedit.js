$(function() {
	$("input[name=Save]").bind("click", save);
});

function save() {
	if (formSubmit()) {
		var data={};
		data.id=$('#id').val();
		data.title=$('#title').val();
		data.img=$("input[name='img']").val();
		data.content=contEditor.html();
		data.state=$("input[name=state]:checked").val();
		/*alert($("input[name='titlePic']").val());*/
		data.titlePic = $("input[name='titlePic']").val();
		console.log(data);
		var jsonData = JSON.stringify(data); 
		// 防止重复提交 点击保存后隐藏按钮
		$("input[name='Save']").hide();
		$.ajax({
			url : "/zoo/news/editlist",
			type : "Post",
			data : {"data":jsonData},
			dataType : "json",
			success : function(data) {
				if (data.code == 0) {
					Dalert("保存成功！", "", function() {
						window.location.href = "/zoo/news/list";
					});
				} else {
					$("input[name='Save']").show();
					Dalert(data.desc);
				}
			},
			error : function(xhr, status, error) {
				alert(xhr.responseText);
			}
		});
	}
}

function formSubmit() {
	if($("#title").val()==''){
		Dalert("请填写标题！");
		return false;
	}
	if(contEditor.isEmpty()){
		Dalert("请上传标题小图标！");
		return false;
	}
	if($("input[name='img']").val()==''){
		Dalert("请上传主题图片！");
		return false;
	}
	if(contEditor.isEmpty()){
		Dalert("请填写详情！");
		return false;
	}
	return true;

}