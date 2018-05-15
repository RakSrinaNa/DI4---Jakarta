Vue.component("GoldenBookEntry", { 
  props: ['entry', 'user'],
  template: `
  <sui-card>
    <sui-card-content>
      <sui-card-header>{{ gbe.title }}</sui-card-header>
      <sui-card-meta>{{ gbe.updateDate }}</sui-card-meta>
      <sui-card-description>
        <p>
          {{ gbe.body }}
        </p>
      </sui-card-description>
    </sui-card-content>
    <sui-card-content extra>
      <sui-rating :rating="rate" :max-rating="5" @rate="handleRate" />
    </sui-card-content>
  </sui-card>
`,
  data() {
    return {
      gbe: this.entry,
      rate: this.entry.rating
    }
  },
  methods: {
    handleRate(evt, props) {
      this.rate = props.rating;
      var that = this;
      var rateEntry  = async function(entry, rating, user){
        that.$emit('loading-toggle')
        try {
          const response = await axios.put(`ws/goldenbook/rating`,
            { 
              entryId: entry.id, 
              userId: user.id, 
              rating: props.rating
            }
          )
        } catch (e) {
          this.error = e
        }
        that.$emit('loading-toggle')
      }
      rateEntry(this.gbe, props.rating, this.user)
    }
  }
}
)