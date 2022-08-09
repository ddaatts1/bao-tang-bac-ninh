        var toDay = new Date();
        var date = 'Ngày ' + toDay.getDate() + ' Tháng ' + (toDay.getMonth() + 1) + ' Năm ' + toDay.getFullYear();
        document.getElementById('time').innerHTML = date;