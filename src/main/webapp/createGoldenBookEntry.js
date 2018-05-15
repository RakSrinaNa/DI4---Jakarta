Vue.component('GoldenBookEntryCreate', {
    template: `
    <sui-modal v-model="modalOpen">
    <sui-modal-header><sui-label ribbon=true color=red><h2>Create a Golden Book Entry</h2></sui-label></sui-modal-header>
    <sui-modal-content>
      <sui-modal-description>
        <sui-message error v-if="error !== ''">
        <sui-message-header> {{ error }}</sui-message-header>
        </sui-message>
        <div class="ui form">
            <div class="field">
                <label>Title</label>
                <input placeholder="Title" v-model="entry.title" />
            </div>
            <div class="field">
                <label>Body</label>
                <textarea placeholder="Body" v-model="entry.body"></textarea>
            </div>
        </div>
      </sui-modal-description>
    </sui-modal-content>
    <sui-modal-actions>
    <div is="sui-button-group" floated="right">
        <sui-button @click="dismiss">Cancel</sui-button>
        <sui-button-or />
        <sui-button positive @click="toggle">Save</sui-button>
    </div>
    </sui-modal-actions>
  </sui-modal>
    `,
  props: ['modalOpen'],
  data: function(){
    return {
        entry: {
            title: "",
            body: ""
        },
        error: ""
    }
},
  methods:{
      toggle: function(){
          if (this.entry.body === '' || this.entry.title === ''){
              this.error = "You must fill in both fields !"
              return
          }
          var that = this
          var createEntry  = async function(entry){
            that.$emit('loading-toggle')
            try {
              const response = await axios.post(`ws/goldenbook`, that.entry)
            } catch (e) {
              this.error = e
              console.error(e);
            }
            that.$emit('loading-toggle')
            that.$emit('entries-reload')
            that.entry = {};
          }
          createEntry(this.entry)
          this.$emit('modal-toggle')
      },
      dismiss: function(){
          this.$emit('modal-toggle')
          this.entry = {}
          this.error = ""
      }
  }
})