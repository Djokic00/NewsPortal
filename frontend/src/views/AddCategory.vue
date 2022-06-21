<template>
  <div class="pt-5">
    <form method="post" v-on:submit.prevent = "postCategory()" >
      <div class="form-group">
        <label for="ime">Title</label>
        <input style="margin-top: 10px;" required  v-model="name" type="text" class="form-control" id="ime" placeholder="Enter name">
      </div>
      <div class="form-group">
        <label for="content" style="margin-top: 10px;">Content</label>
        <input style="margin-top: 10px;" required  v-model="description" type="text" class="form-control" id="content" placeholder="Enter desc">
      </div>
      <br>
      <button type="submit" class="btn btn-primary mt-2">Add Category</button>
    </form>

    <h1 class="mt-4">Category</h1>
    <div class="row" style="display:inline;">
      <div class="col-4 mx-auto" >
        <table class=" table text-center" style="width: 650px;margin-left: -150px;">
          <thead>
          <tr>
            <th scope="col">Name</th>
            <th scope="col">Description</th>
            <th scope="col">Delete</th>
            <th scope="col">Edit</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="category in categoryList" :key="category.name">
            <b-card style="margin-top: 10px">
              <td scope="row" @click="findCategory(category.name)"> {{ category.name }}</td>
            </b-card>
            <td scope="row"> {{ category.description }}</td>
            <td scope="row">
              <b-button v-if=" findNumberOfNews(category.name) < 1" @click="deleteCategory(category.name)" size="sm">Delete</b-button>
            </td>
            <td scope="row">
              <b-button @click="editCategory(category.name)">Edit</b-button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "AddCategory",
  data() {
    return {
      date: '',
      name: null,
      description: null,
      categoryList: [],
    }
  },
  mounted() {
    this.$axios.get('/api/category').then((response) => {
      this.categoryList = response.data;
    })
  },
  methods: {
    postCategory() {
      let alreadyExists = 0;
      for (let i = 0; i < this.categoryList.length; i++) {
        if (this.categoryList[i].name === this.name) {
          alreadyExists = 1;
        }
      }
      if (alreadyExists === 1) this.$alert("Category with that name already exists")
      else {
        this.$axios.post('/api/category', {
          "name": this.name,
          "description": this.description
        }).then(() => {
          window.location.reload()
        })
      }
    },
    findCategory(name) {
      this.$router.push(`/news/category/${name}`);
    },
    findNumberOfNews(name) {
      let value = 10;
      this.$axios.get(`/api/news/category/${name}`).then((response) => {
        this.newsList = response.data;
        value = response.data.length;
      })
      return value;
    },
    editCategory(name) {
      this.$router.push(`/category/${name}`)
    },
    deleteCategory(name) {
      this.$axios.delete(`api/category/${name}`).then(() => {
        this.categoryList.splice(name, 1);
      });
      window.location.reload()
    },
  }
}
</script>

<style scoped>

</style>
