function progressHandlingFunction(e) {
    if (e.lengthComputable) {
        $('#progress').attr({value: e.loaded, max: e.total}); //更新数据到进度条
        var mydate = new Date();
        var time1 = mydate.getTime();
        var speed = (e.loaded / 1024) / ((time1 - time) / 1000);
        var percent = e.loaded / e.total * 100;
        $('#progress').html(percent.toFixed(2) + "%   " + parseInt(speed) + "KB/S");
        $('#progress').css('width', percent.toFixed(2) + "%");
    }
}


