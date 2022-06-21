<template>
  <div class="pt-5">
    <form method="post" v-on:submit.prevent = "postUser()" >
      <div class="form-group">
        <label for="email">Email</label>
        <input style="margin-top: 10px;" required  v-model="email" type="email" class="form-control" id="email" placeholder="Enter email">
      </div>
      <br>
      <div class="form-group">
        <label for="firstName" style="margin-top: 10px;">Firstname</label>
        <input style="margin-top: 10px;" required  v-model="first_name" type="name" class="form-control" id="firstName" placeholder="Enter name">
      </div>
      <br>
      <div class="form-group">
        <label for="lastName" style="margin-top: 10px;">Lastname</label>
        <input style="margin-top: 10px;" required  v-model="last_name" type="lastname" class="form-control" id="lastName" placeholder="Enter lastname">
      </div>
      <br>
      <div class="form-group">
        <label for="password" style="margin-top: 10px;">Password</label>
        <input style="margin-top: 10px;" required v-model="password" type="password" class="form-control"  id="password" placeholder="Enter Password">
      </div>
        <br>
        <div class="form-group">
          <label for="password_confirmation" style="margin-top: 10px;">Confirm password</label>
          <input style="margin-top: 10px;" required v-model="passwordConfirmation" type="password" class="form-control" id="password_confirmation" placeholder="Confirm Password">
        </div>
        <small v-if="password != passwordConfirmation"><b style="color: red">Passwords do not match.</b></small>
      <br>
      <div class="form-group">
        <label for="type" style="margin-top: 10px;">Type - 1 for Admin, 0 for Content Creator</label>
        <input style="margin-top: 10px;" required  v-model="type" type="number" class="form-control" id="type" placeholder="Enter type">
      </div>
      <br>
      <div class="form-group">
        <label for="status" style="margin-top: 10px;">Status</label>
        <input style="margin-top: 10px;" required  v-model="status" type="number" class="form-control" id="status" placeholder="Enter status">
      </div>
      <br>
      <button type="submit" class="btn btn-primary mt-2">Add user</button>
    </form>

    <h1 class="mt-4">Users</h1>
    <div class="row" style="display:inline;">
      <div class="col-4 mx-auto" >
        <table class=" table text-center" style="width: 650px;margin-left: -150px;">
          <thead>
          <tr>
            <th scope="col">Email</th>
            <th scope="col">Name</th>
            <th scope="col">Type</th>
            <th scope="col">Status</th>
            <th scope="col">Edit</th>
            <th scope="col">Change Status</th>
          </tr>
          </thead>
          <tbody >
          <tr v-for="user in users" :key="user.email" >
            <b-card style="margin-top: 10px">
              <td scope="row"> {{ user.email }}</td>
            </b-card>
            <td scope="row"> {{ user.first_name }}</td>
            <td scope="row"> {{ user.type }}</td>
            <td scope="row"> {{ user.status }}</td>
            <td scope="row">
              <b-button @click="editUser(user.email)" size="sm">Edit</b-button>
            </td>
            <td scope="row" v-if="user.type === 0">
              <b-button  @click="changeStatus(user.email)" size="sm">Change</b-button>
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
  name: "AddUser",
  data() {
    return {
      date: '',
      email: null,
      first_name: null,
      last_name: null,
      password: null,
      status: null,
      type: null,
      users: [],
      passwordConfirmation: '',
    }
  },
  mounted() {
    this.$axios.get('/api/users').then((response) => {
      this.users = response.data;
    });
  },
  methods: {
    editUser(email) {
      this.$router.push(`/users/${email}`);
    },
    postUser() {
      let error = 0;
      for (let i = 0; i < this.users.length; i++) {
        if (this.users[i].email === this.email) {
          this.$alert("Email already in use!")
          error = 1;
        }
      }
      if (this.password != this.passwordConfirmation){
        this.$alert("Passwords do not match.");
        error = 1;
      }
      if (error === 0) {
        this.$axios.post('/api/users', {
          "email": this.email,
          "first_name": this.first_name,
          "last_name": this.last_name,
          "password": this.password,
          "status": this.status,
          "type": this.type,
        }).then(() => {
          window.location.reload()
        })
      }
    },
    changeStatus(email){
      this.$axios.get(`/api/users/status/${email}`).then(() => {
        window.location.reload()
      })
    },
  }
}
</script>

<style scoped>

</style>
