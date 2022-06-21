<template>
  <div>
    <h2>
      Title: {{ news.title }}
    </h2>
    <h7>
      Visits: {{ news.visits }}
    </h7>

    <h6 style="margin-top: 18px">
      Category: {{news.category.name}}
    </h6>

    <h6 style="margin-top: 12px">Tags:</h6>
    <h6 v-for="tag in tagList" :key = "tag.id">
      {{tag.tag_name}}
    </h6>

    <h5  style="margin-top: 12px">
      Author: {{ news.author.first_name }}
    </h5>

    <h6  style="margin-top: 12px">
      Creation date: {{new Date(news.creation_date).toISOString().split('T')[0] }}
    </h6>
    <br>
    <h4>Content:</h4>
    <p>
      {{ news.content }} <br><br>
    </p>
  </div>
</template>

<script>

export default {
  name: "TopNews",
  props: {
    news: {
      type: Object,
      required: true,
    }
  },
  data() {
    return {
      tagList: []
    }
  },
  mounted() {
    this.$axios.get(`/api/news/newsTag/${this.$route.params.id}`).then((response) => {
      this.tagList = response.data;
    })
  }
}
</script>

<style scoped>

</style>
