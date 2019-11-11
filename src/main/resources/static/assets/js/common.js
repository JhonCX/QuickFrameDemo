/** EasyWeb iframe v3.1.5 date:2019-10-05 License By http://easyweb.vip */

// 以下代码是配置layui扩展模块的目录，每个页面都需要引入
layui.config({
    version: '315',
    base: getProjectUrl() + 'assets/module/'
}).extend({
    formSelects: 'formSelects/formSelects-v4',
    treetable: 'treetable-lay/treetable',
    dropdown: 'dropdown/dropdown',
    notice: 'notice/notice',
    step: 'step-lay/step',
    dtree: 'dtree/dtree',
    citypicker: 'city-picker/city-picker',
    tableSelect: 'tableSelect/tableSelect',
    Cropper: 'Cropper/Cropper',
    zTree: 'zTree/zTree',
    introJs: 'introJs/introJs',
    fileChoose: 'fileChoose/fileChoose',
    tagsInput: 'tagsInput/tagsInput',
    Drag: 'Drag/Drag',
    CKEDITOR: 'ckeditor/ckeditor',
    Split: 'Split/Split',
    cascader: 'cascader/cascader'
}).use(['layer', 'admin'], function () {
    var $ = layui.jquery;
    var layer = layui.layer;
    var admin = layui.admin;

    // 移除loading动画
    setTimeout(function () {
        admin.removeLoading();
    }, window == top ? 600 : 100);

});

// 获取当前项目的根路径，通过获取layui.js全路径截取assets之前的地址
function getProjectUrl() {
    var layuiDir = layui.cache.dir;
    if (!layuiDir) {
        var js = document.scripts, last = js.length - 1, src;
        for (var i = last; i > 0; i--) {
            if (js[i].readyState === 'interactive') {
                src = js[i].src;
                break;
            }
        }
        var jsPath = src || js[last].src;
        layuiDir = jsPath.substring(0, jsPath.lastIndexOf('/') + 1);
    }
    return layuiDir.substring(0, layuiDir.indexOf('assets'));
}

const SUCCESS_CODE = 1;
const FAIL_CODE = -1;

function noticeSuccess(msg) {
    let notice = layui.notice;
    notice.success({
        title: '通知',
        position: 'topRight', //位置 bottomRight、bottomLeft、topRight、 topLeft、 topCenter、 bottomCenter 、 center。
        transitionIn: 'fadeIn', //入场动画 bounceInLeft, bounceInRight, bounceInUp, bounceInDown, fadeIn, fadeInDown, fadeInUp, fadeInLeft, fadeInRight , flipInX。
        transitionOut: 'fadeOut', //出场动画 fadeOut, fadeOutUp, fadeOutDown, fadeOutLeft, fadeOutRight, flipOutX。
        timeout: '1000', //显示时间
        progressBar: 'false',//进度条
        balloon: 'true',//气泡效果
        close: 'true',//关闭效果
        animateInside: 'true', //	文字动画效果
        theme: 'light',//主题 light、dark
        audio: '1',//音效	 1，2，3，4，5，6
        pauseOnHover: 'false',//鼠标滑过暂停消失时间
        resetOnHover: 'false',//鼠标滑过重置消失时间
        message: msg,
    });
}

function noticeError(msg) {
    let notice = layui.notice;
    notice.error({
        title: '通知',
        position: 'topRight',
        transitionIn: 'fadeIn',
        transitionOut: 'fadeOut',
        timeout: '2000',
        progressBar: 'false',
        balloon: 'true',//气泡效果
        close: 'true',//关闭效果
        animateInside: 'true', //	文字动画效果
        theme: 'light',//主题 light、dark
        audio: '1',//音效	 1，2，3，4，5，6
        pauseOnHover: 'false',//鼠标滑过暂停消失时间
        resetOnHover: 'false',//鼠标滑过重置消失时间
        message: msg
    });
}


