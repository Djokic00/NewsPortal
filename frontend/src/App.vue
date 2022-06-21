<template>
  <div>
    <NavigationBar/>
    <div class="container">
      <router-view/>
    </div>
  </div>
</template>

<script>
import NavigationBar from "./components/NavigationBar";
export default {
  components: {NavigationBar},
}
</script>

<style>

</style>
