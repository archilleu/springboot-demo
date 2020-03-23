(function ($) {
    var id = null;
    var table = null;
    var baseUrl = null;
    var listUrl = null;
    var $editForm = null;
    var curClickBtn = null;

    function initTable(args) {
        if (!args.url) {
            $.error("url can't empty");
            return;
        }
        if (!args.cols) {
            $.error("cols can't empty");
            return;
        }

        //表单数据id
        id = args.id;

        var params = {
            url: args.url,
            cols: args.cols,
            elem: args.elem,
            id: "hyTable",
            page: args.page || true,
            loading: args.loading || true,
            title: args.title || "标题",
            autoSort: args.autoSort || true,
            parseData: (res) => {
                return {
                    "code": 0,
                    "msg": "success",
                    "count": res.count,
                    "data": res.list
                };
            },
            done: (res, curr, count) => {
                args.done && args.done(res, curr, count);
            }
        }

        table = layui.table.render(params);
        return table;
    }

    function initToolbar(toolbar) {
        const buttons = toolbar.buttons;
        for (var i = 0; i < buttons.length; i++) {
            const button = buttons[i];
            $(button.elem).click(function (e) {
                //提示消息，默认
                if (!button.msg) {
                    button.msg = {}
                    button.msg.success = "成功";
                    button.msg.tip = "确认操作";
                    button.msg.error = "失败";
                }

                //设置当前点击按钮
                curClickBtn = button;

                if (button.type == "add" || button.type == "modify") {
                    initOpenLayerButton(button);
                } else if (button.type == "delete") {
                    initAjaxButton(button);
                } else {
                    //其他按钮,只是绑定点击事件
                    button.click(e);
                }

                return;
            });

            function initOpenLayerButton(button) {
                //重置编辑表单
                reset();

                //是否需要获取数据
                if (button.type == "modify") {
                    if (false == getData(button.beforeShow))
                        return;
                }

                //打开编辑表单
                formLayerId = layer.open({
                    title: button.layer.title,
                    area: ['90%', '90%'],
                    type: 1,
                    shadeClose: true,
                    maxmin: true,
                    content: $(button.layer.content)
                });
            }

            function initAjaxButton(button) {
                var selected = getSelections()
                if (selected.length == 0) {
                    layer.msg("请选择一条记录")
                    return;
                }

                layer.confirm((button.msg.tip || "确定执行操作?"), {
                    icon: 3,
                    title: '提示'
                }, function (index) {
                    $.ajax({
                        type: "delete",
                        url: baseUrl + selected[0][id],
                        beforeSend: (xhr) => {},
                        success: (data) => {
                            layer.msg(button.msg.success || "操作成功");
                            reload();
                        },
                        error: (data) => {
                            if (!data.responseJSON) {
                                data.responseJSON = JSON.parse(data.responseText);
                            }
                            layer.msg(data.responseJSON.message);
                        },
                        complete: (data) => {}
                    });
                });
            }

            function getData(callback) {
                var selected = getSelections()
                if (selected.length == 0) {
                    layer.msg("请选择一条记录")
                    return false;
                }
                $.ajax({
                    type: "get",
                    url: baseUrl + selected[0][id] + ".json",
                    dataType: "json",
                    success: (data) => {
                        callback && callback(data);
                        $.each(data, function (key, val) {
                            $editForm.find("[name=" + key + "]").val(val);
                        });
                    },
                    error: (data) => {
                        if (!data.responseJSON) {
                            data.responseJSON = JSON.parse(data.responseText);
                        }
                        layer.msg(data.responseJSON.message);
                    }
                });

                return true;
            }
        }

        //提交添加修改请求
        layui.form.on('submit(formSubmit)', function (data) {
            function disable($item) {
                $item.attr('disabled', 'disabled')
                $item.addClass("layui-btn-disabled");
            }

            function enable($item) {
                $item.removeAttr("disabled");
                $item.removeClass("layui-btn-disabled");
            }

            //提交前处理数据
            if (curClickBtn.beforeSend) {
                if (false == curClickBtn.beforeSend(data.field))
                    return false;
            }

            var $submit = $(data.elem);
            $.ajax({
                type: curClickBtn.method || "POST",
                url: baseUrl + curClickBtn.url,
                data: JSON.stringify(data.field),
                contentType: "application/json; charset=utf-8",
                beforeSend: (xhr) => {
                    disable($submit);
                },
                success: (data) => {
                    layer.open({
                        content: curClickBtn.msg.success || "操作成功",
                        btn: ['确定', '取消'],
                        yes: (index, layero) => {
                            layer.close(index);
                            layer.close(formLayerId)
                        },
                    });

                    reload();
                },
                error: (data) => {
                    if (!data.responseJSON) {
                        data.responseJSON = JSON.parse(data.responseText);
                    }
                    layer.msg(data.responseJSON.message);
                },
                complete: (data) => {
                    enable($submit);
                    curClickBtn.complete && curClickBtn.complete(data);
                }
            });

            return false;
        });
    }

    function initSearch(search) {
        $(search.elem).click(() => {
            var where = {};
            $.each(search.where, function (key, val) {
                where[key] = $(val).val();
            });
            table.reload({
                url: listUrl,
                where: where
            });

            return false;
        });
    }

    //编辑框重置
    function reset() {
        $editForm.find("button[type=reset]").click();
    }

    //刷新table
    function reload() {
        table.reload({
            url: listUrl
        });

        return false;
    }

    //获取选中的记录
    function getSelections() {
        var checkStatus = layui.table.checkStatus(table.config.id);
        var selected = checkStatus.data;
        return selected;
    }

    $.fn.hyTable = function (option) {
        var methods = {
            option: option,
            init: function (args) {
                if (!args.baseUrl) {
                    $.error("option.baseUrl can't empty");
                    return;
                }
                if (!args.list) {
                    $.error("option.list can't empty");
                    return;
                }

                //初始化编辑框元素
                if (args.editElem) {
                    $editForm = $(args.editElem);
                }

                //根路径
                baseUrl = args.baseUrl;
                listUrl = baseUrl + args.list.url;

                //初始化table
                args.list.elem = this.selector;
                args.list.url = listUrl;
                table = initTable(args.list);

                //初始化toolbar
                initToolbar(args.toolbar);

                //初始化search
                initSearch(args.search);

                return this;
            },
            getSelections: function () {
                return getSelections();
            },
            // 刷新记录
            refresh: function () {
                reload();
            },
        };
        if (methods[option]) {
            return methods[option].apply(this, Array.prototype.slice.call(arguments, 1));
        } else if (typeof option === 'object' || !option) {
            return methods.init.apply(this, arguments);
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.tooltip');
        }
    };
})(jQuery);