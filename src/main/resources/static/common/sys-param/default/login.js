var Main = {
  data() {
    return {
      loading: false,
      form: {
        username: null,
        password: null,
        rememberMe: null
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
        ],
      }
    }
  },

  methods: {
    onSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          axios.post('/auth/form',
            this.toFormData(this.form))
            .then(
              response => {
                this.saveUserInfo(response.data);
                window.location.href = "/main"
              })
            .catch(error => {
              this.$message.error({
                message: error.data,
                center: true
              });
            })
            .then(() => {
              this.loading = false;
            });
        } else {
          return false;
        }

      });
    },
    saveUserInfo(userInfo) {
      sessionStorage.setItem("user", JSON.stringify(userInfo));
    },
    toFormData(loginInfo) {
      const formData = new URLSearchParams();
      formData.append('username', loginInfo.username);
      formData.append('password', loginInfo.password);
      formData.append('remember-me', loginInfo.rememberMe);
      return formData;
    }
  }
}
var App = Vue.extend(Main);
new App().$mount('#app');
