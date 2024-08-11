# Event handler for the donation form submission
document.addEventListener 'DOMContentLoaded', ->
  donateForm = document.querySelector '#donate-form'

  if donateForm
    donateForm.addEventListener 'submit', (event) ->
      event.preventDefault()  # Prevent the default form submission

      # Get form data
      amount = donateForm.querySelector '#amount'?.value
      name = donateForm.querySelector '#name'?.value
      email = donateForm.querySelector '#email'?.value

      # Validate form fields
      if amount and name and email
        # Simulate form submission
        fetch '/donate',  # Your server endpoint
          method: 'POST'
          headers:
            'Content-Type': 'application/json'
          body: JSON.stringify(
            amount: amount
            name: name
            email: email
          )
        .then (response) ->
          if response.ok
            return response.json()
          else
            throw new Error 'Failed to submit donation'

        .then (data) ->
          # Handle success response
          alert 'Thank you for your donation!'
          donateForm.reset()

        .catch (error) ->
          # Handle error response
          alert 'An error occurred: ' + error.message
      else
        alert 'Please fill out all fields.'

