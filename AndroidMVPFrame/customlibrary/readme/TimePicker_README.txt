
-------------------------------下面这段代码在最大限度上自定义事件控件的view的显示方式---------------------------

        // 显示年月日时分秒

        TimePickerDialog mDialogAll;
        long tenYears = 10L * 365 * 1000 * 60 * 60 * 24L;
        mDialogAll = new TimePickerDialog.Builder()
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        // 日期选择回调
                        ToastUtil.showCenterToast(MainActivity.this, DateUtils.formatDate(millseconds,"yyyy-MM-dd"));
                    }
                })
                .setCancelStringId("取消") // 取消日期控件显示
                .setSureStringId("确定") // 时间选择
                .setTitleStringId("选择时间")//日期选择控件标题
                .setYearText("年")
                .setMonthText("月")
                .setDayText("日")
                .setHourText("时")
                .setMinuteText("分")
                .setCyclic(false)//月，日，时，分，秒，是否循环显示
                .setMinMillseconds(System.currentTimeMillis())//最小日期
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)//最大日期
                .setCurrentMillseconds(System.currentTimeMillis())//当前日期
                .setThemeColor(getResources().getColor(R.color.timepicker_dialog_bg))//日期标题背景色
                .setType(DateTimeType.ALL)// 日期显示方式
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color)) //日期字体色
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.timepicker_toolbar_bg)) // 当前选中的日期的字体色
                .setWheelItemTextSize(12)//日期字体大小
                .build();
        mDialogAll.show(getSupportFragmentManager(), DateTimeType.ALL.name());



-----------------------------下面这段代码基本是托管给了时间控件本身-----------------------------------

//显示方式只显示年月日

TimePickerDialog mDialogYearMonth = new TimePickerDialog.Builder()
                .setType(DateTimeType.YEAR_MONTH_DAY)
                .setThemeColor(getResources().getColor(R.color.colorPrimary))
                .setCallBack(new OnDateSetListener() {
                    @Override
                    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
                        ToastUtil.showCenterToast(MainActivity.this, DateUtils.formatDate(millseconds,"yyyy-MM-dd"));
                    }
                })
                .build();
        mDialogYearMonth.show(getSupportFragmentManager(),DateTimeType.YEAR_MONTH_DAY.name());