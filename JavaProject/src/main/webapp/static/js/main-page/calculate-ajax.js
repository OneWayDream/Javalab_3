function calculate_ajax() {
    $('#calculate').click( function () {
            let minion = $('#minion').val();
            let tier = $('#tier').val();
            let fuel = $('#fuel').val();
            let upgrade1 = $('#upgrade1').val();
            let upgrade2 = $('#upgrade2').val();
            let errorPanel = document.getElementById('errorPanel');
            let warningPanel = document.getElementById('warningPanel');
            let resultPanel = document.getElementById('resultPanel');

            let calculateData = 'minion=' + minion + '&tier=' + tier + '&fuel=' + fuel
                + '&upgrade1=' + upgrade1 + '&upgrade2=' + upgrade2;
            $.ajax({
                // contentType: "application/json;charset=UTF-8",
                type: "POST",
                url: "main",
                data: calculateData,
                beforeSend : function () {
                //some implementation
                },
                success: function (data) {
                    if (data['ex']!=null){
                        warningPanel.style.display = null;
                        resultPanel.style.display = null;
                        errorPanel.style.display = "block";
                        errorPanel.textContent = "Error: " + data['ex'];
                    } else {
                        if (data['wm']!=null){
                            errorPanel.style.display = null;
                            warningPanel.style.display = "block";
                            resultPanel.style.display = "block";
                            warningPanel.textContent = "Warning: " + data['wm'];
                            resultPanel.textContent = "Result: " + data['result'] + " coins per 24 hours.";
                        } else {
                            errorPanel.style.display = null;
                            warningPanel.style.display = null;
                            resultPanel.style.display = "block";
                            resultPanel.textContent = "Result: " + data['result'] + " coins per 24 hours.";
                        }
                    }
                }
            })
        }
    );
}