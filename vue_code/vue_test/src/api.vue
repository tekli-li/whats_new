<script setup>
    import { onMounted, ref } from 'vue';
    const count = ref(0);
    function increament(){
        count.value++;
    }
    onMounted(()=>{
        console.log("vue has been mounted");
    })
</script>

<template>
    <button @click="increament">
        count:{{ count }}
    </button>
</template>

<style>

</style>