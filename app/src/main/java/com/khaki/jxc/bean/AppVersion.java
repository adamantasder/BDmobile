package com.khaki.jxc.bean;

public class AppVersion {

        //app 名字
        private String appName;
        //服务器版本
        private String serverVersion;
        //强制升级
        private String lastForce;
        //app最新版本地址
        private String updateUrl;
        //升级信息
        private String upgradeInfo;

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public String getServerVersion() {
            return serverVersion;
        }

        public void setServerVersion(String serverVersion) {
            this.serverVersion = serverVersion;
        }

        public String getLastForce() {
            return lastForce;
        }

        public void setLastForce(String lastForce) {
            this.lastForce = lastForce;
        }

        public String getUpdateUrl() {
            return updateUrl;
        }

        public void setUpdateUrl(String updateUrl) {
            this.updateUrl = updateUrl;
        }

        public String getUpgradeInfo() {
            return upgradeInfo;
        }

        public void setUpgradeInfo(String upgradeInfo) {
            this.upgradeInfo = upgradeInfo;
        }
    }

