<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
  export default{
    name: 'App',
    methods: {
      fetchToken() {
        let _this = this;
        let token = _this.getSessionStorage('token');
        if (!token) {
          this.ajax(this.apiType().getToken, this.serviceType().api, {}, function (responseData) {
            _this.setSessionStorage('token', responseData.token);
            _this.$router.addRoutes(_this.constantRouterMap);
          });
        } else {
          _this.$router.addRoutes(_this.constantRouterMap);
        }
      }
    },
    created() {
      this.fetchToken();
    }
  }
</script>
<style lang="scss">
  @import './styles/index.scss'; // 全局自定义的css样式
  .el-loading-mask {
    background-color: rgba(0, 0, 0, 0.5);
  }
</style>
