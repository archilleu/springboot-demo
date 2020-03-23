(function ($) {
    $.fn.hySidebar = function () {
        var $sidebar = $(this);
        var $sidebarBtn = $sidebar.find(">div:first-child");
        $sidebarBtn.click(() => {
            var width = "-" + $sidebar.outerWidth() + "px";
            var right = $sidebar.css("right");
            if ("0px" == right) {
                $sidebar.css("right", width);
            } else {
                $sidebar.css("right", "0px");
            }
        });
    }
})(jQuery);