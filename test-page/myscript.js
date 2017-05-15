window.onload = function() {
      var enable_button = document.getElementById("enable_button");
      var scroll_button = document.getElementById("scroll_button");
      var clickBtn = document.getElementsByClassName('click')[0];

      // Disable the button on initial page load
      enable_button.disabled = true;

      //add event listener
      clickBtn.addEventListener('click', function(event) {
        enable_button.disabled = !enable_button.disabled;
      });

      enable_button.addEventListener('click', function(event) {
        alert('Enabled!');
      });

      scroll_button.addEventListener('click', function(event) {
        document.documentElement.style["height"] = "5000px";
      });
};

