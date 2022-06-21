<template>
  <div class="pt-5">
    <h2 class="mt-4">Edit News</h2>
    <br>

        <form method="post" v-on:submit.prevent = "editNews()" >
          <div class="form-group">
            <label for="naslov">Title</label>
            <input style="margin-top: 10px;" v-model="vest.title" v-text="vest.title" type="text" class="form-control" id="naslov" placeholder="Enter title">
          </div>
          <div class="form-group">
            <label for="tekst" style="margin-top: 10px;">Content</label>
            <textarea style="margin-top: 10px;" cols="40" rows="5" v-model="vest.content" v-text="vest.content" type="text" class="form-control" id="tekst" placeholder="Enter content"></textarea>
          </div>
          <br>
          <div class = "row"  style="text-align: center">
            <div class="col form-group">
              <b-form-select v-model = "selectCategory"  class="m-3">
                <b-form-select-option  v-for="category in categoryList" :key="category.name" :value= "category" >{{category.name}}</b-form-select-option>
              </b-form-select>
            </div>
            <div class="col" >
              <router-link :to="{name: 'AddCategory'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'AddCategory'}">Add category</router-link>
            </div>
            <div class="col form-group">
              <b-form-select v-model = "selectUsers"   class="m-3">
                <b-form-select-option v-for="user in users" :key="user.email" :value= "user" >{{user.first_name}}</b-form-select-option>
              </b-form-select>
            </div>

          </div>
          <br>  <br>
          <br>
          <div style="text-align: center">
            <button  type="submit" class="btn btn-primary">Save</button>
          </div>
        </form>
      </div>
</template>
<script>

export default {
  name: "EditNews",
  props: {
    news: {
      type: Object,
      required: true,
    }
  },
  data() {
    return {
      vest: null,
      title: null,
      content: null,
      categoryList: [],
      users: [],
      selectCategory: [],
      selectUsers: [],
      tags: [],
    }
  },
  mounted() {
    this.$axios.get(`/api/news/${this.$route.params.id}`).then((response) => {
      this.vest = response.data;
    });
    this.$axios.get('/api/category').then((response) => {
      this.categoryList = response.data;
    });
    this.$axios.get('/api/users').then((response) => {
      this.users = response.data;
    });
  },
  methods: {
    editNews() {
      var x = 0;
      this.$axios.post(`/api/news/update`, {
        "id": this.$route.params.id,
        "title": this.vest.title,
        "content": this.vest.content,
        "visits": x,
        "author": this.selectUsers,
        "category":this.selectCategory,
        "tag": this.tags
      }).then(() => {
        this.$router.push(`/news`);
      });
    },
  }
}
</script>

<style scoped>

</style>
