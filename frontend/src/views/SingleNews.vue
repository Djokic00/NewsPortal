<template>
  <div>
    <NewsCom :news="news"></NewsCom>
  </div>
</template>

<script>
import NewsCom from "../components/NewsCom";
export default {
  name: "SingleNews",
  components: {NewsCom},
  data() {
    return {
      news: {},
    }
  },
  created() {
    this.$axios.get(`/api/news/${this.$route.params.id}`).then((response) => {
      this.news = response.data;
    });
  }
}
</script>

<style scoped>

</style>
