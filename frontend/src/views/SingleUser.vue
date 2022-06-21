<template>
  <div>
    <UserCom :korisnici="user"></UserCom>
  </div>
</template>

<script>
import UserCom from "../components/UserCom";
export default {
  name: "SingleUser",

  components: {UserCom},
  data() {
    return {
      user: {},
    }
  },
  created() {
    this.$axios.get(`/api/users/${this.$route.params.email}`).then((response) => {
      this.user = response.data;
    });
  }
}
</script>

<style scoped>

</style>
