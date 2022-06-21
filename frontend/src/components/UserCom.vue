<template>
  <div class="pt-5">
    <form method="post" v-on:submit.prevent = "editOldUser()" >
      <div class="form-group">
        <label for="email">Email</label>
        <input style="margin-top: 10px;" required  v-model="users.email" v-text="users.email" type="email" class="form-control" id="email" >
      </div>
      <br>
      <div class="form-group">
        <label for="firstName" style="margin-top: 10px;">Firstname</label>
        <input style="margin-top: 10px;" required  v-model="users.first_name" v-text="users.first_name" type="name" class="form-control" id="firstName" placeholder="Enter name">

      </div>
      <br>
      <div class="form-group">
        <label for="lastName" style="margin-top: 10px;">Lastname</label>
        <input style="margin-top: 10px;" required  v-model="users.last_name" v-text="users.last_name" type="lastname" class="form-control" id="lastName" placeholder="Enter lastname">
      </div>
      <br>
      <div class="form-group">
        <label for="password" style="margin-top: 10px;">Password</label>
        <input style="margin-top: 10px;"  required  v-model="password"  type="password" class="form-control" id="password" placeholder="Enter password">
      </div>
      <br>
      <div class="form-group">
        <label for="type" style="margin-top: 10px;">Type</label>
        <input style="margin-top: 10px;" required  v-model="type"  type="number" class="form-control" id="type" placeholder="Enter type">
      </div>
      <br>

      <button type="submit" class="btn btn-primary mt-2">Save changes</button>
    </form>

    <h1 class="mt-4">Users</h1>
    <div class="row" style="display:inline;">
      <div class="col-4 mx-auto" >
        <table class=" table text-center" style="width: 650px;margin-left: -150px;">
          <thead>
          <tr>
            <th scope="col">Email</th>
            <th scope="col">Firstname</th>
            <th scope="col">Lastname</th>
            <th scope="col">Type</th>
          </tr>
          </thead>
          <tbody >

            <b-card style="margin-top: 10px">
              <td scope="row"> {{ korisnici.email }}</td>
            </b-card>
            <td scope="row"> {{ korisnici.first_name }}</td>
            <td scope="row"> {{ korisnici.last_name }}</td>
            <td scope="row"> {{ korisnici.type }}</td>
            <td scope="row"> {{ korisnici.status }}</td>
          </tbody>
        </table>
      </div>

    </div>
  </div>
</template>

<script>


export default {
  name: "UserCom",
  props: {
    korisnici: {
      type: Object,
      required: true,
    }
  },
  data() {
    return {
      email: null,
      first_name: null,
      last_name: null,
      password: null,
      type: null,
      status: null,
      users: null
    }
  },
  mounted() {
    this.$axios.get(`/api/users/${this.$route.params.email}`).then((response) => {
      this.users = response.data;
    });
  },
  methods: {
    editOldUser() {
      this.$axios.post(`/api/users/${this.$route.params.email}`, {
        "email": this.users.email,
        "first_name": this.users.first_name,
        "last_name": this.users.last_name,
        "password": this.password,
        "type": this.type
      }).then(() => {
        this.$router.push(`/users`);
      })
    },

  }
}
</script>

<style scoped>

</style>
