<template>
  <div class="pt-5">
    <h2 class="mt-4">Edit Category</h2>
    <br>
        <form method="post" v-on:submit.prevent = "editCategory()" >
          <div class="form-group">
            <label for="ime">Name</label>
            <input style="margin-top: 10px;" required  v-model="categoryField.name" v-text="categoryField.email" type="text" class="form-control" id="ime" placeholder="Enter name">
          </div>
          <div class="form-group">
            <label for="opis" style="margin-top: 10px;">Description</label>
            <input style="margin-top: 10px;" required  v-model="categoryField.description" v-text="categoryField.description" type="text" class="form-control" id="opis" placeholder="Enter desc">
          </div>
          <br>
          <button type="submit" class="btn btn-primary mt-2">Edit</button>
        </form>
      </div>
</template>
<script>

export default {
  name: "EditCategory",
  data() {
    return {
      categoryField: null,
      name: null,
      description: null,
    }
  },
  created() {
    this.$axios.get(`/api/category/${this.$route.params.name}`).then((response) => {
      this.categoryField = response.data;
    });
  },
  methods: {
    editCategory() {
      this.$axios.post(`/api/category/${this.$route.params.name}`, {
        "name": this.categoryField.name,
        "description": this.categoryField.description
      }).then(() => {
        this.$router.push(`/category`);
      });
    },
  }
}
</script>

<style scoped>

</style>
